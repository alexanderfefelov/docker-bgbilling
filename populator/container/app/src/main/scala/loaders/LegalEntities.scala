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

object LegalEntities {

  def load(json: String): Unit = {
    decode[LegalEntityData](json) match {
      case Right(data) =>
        for (le <- data.legalEntities) {
          sql"alter table contract auto_increment = ${le.id}".update.apply()
          ContractActions.newContract(date = le.contractDate, pattern_id = 4)
          ContractActions.updateContractTitleAndComment(le.id, le.contractNo)
          ContractActions.updateContractPassword(le.id, le.password)
          ContractActions.updateParameterType1(cid = le.id, pid = 1, value = le.login)
          ContractActions.updateParameterType1(cid = le.id, pid = 18, value = le.name)
          le.note1Option.map(x => ContractComment.create(cid = le.id, uid = 0, subject = "Заметка 1", comment = x.trim, dt = DateTime.now, visibled = false))
          le.note2Option.map(x => ContractComment.create(cid = le.id, uid = 0, subject = "Заметка 2", comment = x.trim, dt = DateTime.now, visibled = true))
          le.serviceAddressOption.map(x => ContractActions.updateAddressInfo(cid = le.id, pid = 3, hid = x.houseId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse("")))
          le.legalAddressIdOption.map(x => ContractActions.updateAddressInfo(cid = le.id, pid = 19, hid = x, pod = 0, floor = 0, flat = ""))
          le.billingAddressIdOption.map(x => ContractActions.updateAddressInfo(cid = le.id, pid = 22, hid = x, pod = 0, floor = 0, flat = ""))
          le.phoneOption.map(x => ContractActions.updatePhoneInfo(cid = le.id, pid = 4, phone = x))
          le.emailOption.map(x => ContractActions.updateEmailInfo(cid = le.id, pid = 5, email = x))
          le.notificationOption.map { notification =>
            notification.notificationPhoneOption.map(x => ContractActions.updatePhoneInfo(cid = le.id, pid = 33, phone = x))
            notification.notificationEmailOption.map(x => ContractActions.updateEmailInfo(cid = le.id, pid = 34, email = x))
            notification.notifyByPhone.map(x => ContractActions.updateParameterType5(cid = le.id, pid = 35, value = x))
            notification.notifyByEmail.map(x => ContractActions.updateParameterType5(cid = le.id, pid = 36, value = x))
          }
          le.bankOption.map { bank =>
            bank.bikOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 29, value = x))
            bank.nameOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 30, value = x))
            bank.ksOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 31, value = x))
            bank.rsOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 32, value = x))
          }
          le.budgetaryOption.map(x => ContractActions.updateParameterType5(cid = le.id, pid = 20, value = x))
          le.codesOption.map { codes =>
            codes.innOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 21, value = x))
            codes.kppOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 23, value = x))
            codes.ogrnOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 24, value = x))
            codes.okatoOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 25, value = x))
            codes.oktmoOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 26, value = x))
            codes.okvedOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 27, value = x))
            codes.okpoOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = 28, value = x))
          }
          ContractActions.updateParameterType1(cid = le.id, pid = 38, value = le.portingPriceOption.getOrElse("0"))
          le.domainIdOption.map { x =>
            val responseFuture = contractService.contractDomainUpdate(le.id, x)
            Await.result(responseFuture, 15.seconds)
          }

          groovyBinding.setProperty("id", le.id)
          val accountNumber = accountNumberGenerator.run()
          ContractActions.updateParameterType1(cid = le.id, pid = 2, value = accountNumber.toString)
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
