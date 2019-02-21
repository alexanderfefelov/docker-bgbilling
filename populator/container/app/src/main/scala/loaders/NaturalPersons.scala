package loaders

import com.github.alexanderfefelov.bgbilling.api.action.kernel.ContractActions
import com.github.alexanderfefelov.bgbilling.api.db.repository.ContractComment
import io.circe.generic.auto._
import io.circe.parser._
import org.joda.time.DateTime
import scalikejdbc._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object NaturalPersons {

  def load(json: String): Unit = {
    decode[NaturalPersonList](json) match {
      case Right(data) =>
        for (np <- data.naturalPersons) {
          sql"alter table contract auto_increment = ${np.id}".update.apply()
          ContractActions.newContract(date = np.contractDate, pattern_id = 1)
          ContractActions.updateContractTitleAndComment(np.id, np.contractNo)
          ContractActions.updateContractPassword(np.id, np.password)
          ContractActions.updateParameterType1(cid = np.id, pid = 1, value = np.login)
          ContractActions.updateParameterType1(cid = np.id, pid = 6, value = np.lastName)
          ContractActions.updateParameterType1(cid = np.id, pid = 7, value = np.firstName)
          np.sexIdOption.map(x => ContractActions.updateListParameter(cid = np.id, pid = 39, value = x))
          np.middleNameOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 8, value = x))
          np.note1Option.map(x => ContractComment.create(cid = np.id, uid = 0, subject = "Заметка 1", comment = x.trim, dt = DateTime.now, visibled = false))
          np.note2Option.map(x => ContractComment.create(cid = np.id, uid = 0, subject = "Заметка 2", comment = x.trim, dt = DateTime.now, visibled = true))
          np.idCardOption.map { idCard =>
            ContractActions.updateListParameter(cid = np.id, pid = 9, value = idCard.kind)
            idCard.seriesOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 10, value = x))
            ContractActions.updateParameterType1(cid = np.id, pid = 11, value = idCard.num)
            ContractActions.updateParameterType6(cid = np.id, pid = 12, value = idCard.date)
            idCard.deptCodeOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 13, value = x))
            ContractActions.updateParameterType1(cid = np.id, pid = 14, value = idCard.deptName)
          }
          np.birthDateOption.map(x => ContractActions.updateParameterType6(cid = np.id, pid = 16, value = x))
          np.birthPlaceOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 17, value = x))
          np.serviceAddressOption.map(x => ContractActions.updateAddressInfo(cid = np.id, pid = 3, hid = x.houseId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse("")))
          np.legalAddressIdOption.map(x => ContractActions.updateAddressInfo(cid = np.id, pid = 15, hid = x, pod = 0, floor = 0, flat = ""))
          np.phoneOption.map(x => ContractActions.updatePhoneInfo(cid = np.id, pid = 4, phone = x))
          np.emailOption.map(x => ContractActions.updateEmailInfo(cid = np.id, pid = 5, email = x))
          np.notificationOption.map { notification =>
            notification.notificationPhoneOption.map(x => ContractActions.updatePhoneInfo(cid = np.id, pid = 33, phone = x))
            notification.notificationEmailOption.map(x => ContractActions.updateEmailInfo(cid = np.id, pid = 34, email = x))
            notification.notifyByPhone.map(x => ContractActions.updateParameterType5(cid = np.id, pid = 35, value = x))
            notification.notifyByEmail.map(x => ContractActions.updateParameterType5(cid = np.id, pid = 36, value = x))
          }
          ContractActions.updateParameterType1(cid = np.id, pid = 38, value = np.portingPriceOption.getOrElse("0"))
          np.domainIdOption.map { x =>
            val responseFuture = contractService.contractDomainUpdate(np.id, x)
            Await.result(responseFuture, 15.seconds)
          }

          groovyBinding.setProperty("id", np.id)
          val accountNumber = accountNumberGenerator.run()
          ContractActions.updateParameterType1(cid = np.id, pid = 2, value = accountNumber.toString)
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
