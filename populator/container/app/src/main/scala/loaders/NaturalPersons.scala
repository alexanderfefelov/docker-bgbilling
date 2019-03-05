package loaders

import com.github.alexanderfefelov.bgbilling.api.action.kernel.ContractActions
import com.github.alexanderfefelov.bgbilling.api.db.repository.ContractComment
import com.typesafe.config.Config
import io.circe.generic.auto._
import io.circe.parser._
import org.joda.time.DateTime
import scalikejdbc._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object NaturalPersons {

  def load(config: Config, json: String): Unit = {
    decode[NaturalPersonList](json) match {
      case Right(data) =>
        for (np <- data.naturalPersons) {
          sql"alter table contract auto_increment = ${np.id}".update.apply()
          ContractActions.newContract(date = np.contractDate, pattern_id = 1)
          ContractActions.updateContractTitleAndComment(np.id, np.contractNo)
          ContractActions.updateContractPassword(np.id, np.password)
          ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_LOGIN"), value = np.login)
          ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_LAST_NAME"), value = np.lastName)
          ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_FIRST_NAME"), value = np.firstName)
          np.middleNameOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_MIDDLE_NAME"), value = x))
          np.sexOption.map(x => ContractActions.updateListParameter(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_SEX"), value = x))
          np.note1Option.map(x => ContractComment.create(cid = np.id, uid = 0, subject = "Заметка 1", comment = x.trim, dt = DateTime.now, visibled = false))
          np.note2Option.map(x => ContractComment.create(cid = np.id, uid = 0, subject = "Заметка 2", comment = x.trim, dt = DateTime.now, visibled = true))
          np.idCardOption.map { idCard =>
            ContractActions.updateListParameter(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_ID_CARD_TYPE"), value = idCard.typ)
            idCard.seriesOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_ID_CARD_SERIES"), value = x))
            ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_ID_CARD_NUMBER"), value = idCard.number)
            ContractActions.updateParameterType6(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_ID_CARD_DATE"), value = idCard.date)
            idCard.deptCodeOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_ID_CARD_DEPT_CODE"), value = x))
            ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_ID_CARD_DEPT_NAME"), value = idCard.deptName)
          }
          np.birthDateOption.map(x => ContractActions.updateParameterType6(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_BIRTH_DATE"), value = x))
          np.birthPlaceOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_BIRTH_PLACE"), value = x))
          np.serviceAddressOption.map(x => ContractActions.updateAddressInfo(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_SERVICE_ADDRESS"), hid = x.buildingId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse("")))
          np.legalAddressOption.map(x => ContractActions.updateAddressInfo(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_LEGAL_ADDRESS"), hid = x.buildingId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse("")))
          np.phoneOption.map(x => ContractActions.updatePhoneInfo(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_PHONE"), phone = x))
          np.emailOption.map(x => ContractActions.updateEmailInfo(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_EMAIL"), email = x))
          np.notificationOption.map { notification =>
            notification.notificationPhoneOption.map(x => ContractActions.updatePhoneInfo(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFICATION_PHONE"), phone = x))
            notification.notificationEmailOption.map(x => ContractActions.updateEmailInfo(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFICATION_EMAIL"), email = x))
            notification.notifyByPhone.map(x => ContractActions.updateParameterType5(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFY_BY_PHONE"), value = x))
            notification.notifyByEmail.map(x => ContractActions.updateParameterType5(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFY_BY_EMAIL"), value = x))
          }
          ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_PORTING_PRICE"), value = np.portingPriceOption.getOrElse("0"))
          np.domainIdOption.map { x =>
            val responseFuture = contractService.contractDomainUpdate(np.id, x)
            Await.result(responseFuture, 15.seconds)
          }

          groovyBinding.setProperty("id", np.id)
          val accountNumber = accountNumberGenerator.run()
          ContractActions.updateParameterType1(cid = np.id, pid = config.getInt("CONTRACT_PARAMETER_ACCOUNT_NUMBER"), value = accountNumber.toString)
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
