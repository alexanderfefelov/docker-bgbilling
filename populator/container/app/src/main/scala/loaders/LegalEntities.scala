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

object LegalEntities {

  def load(config: Config, json: String): Unit = {
    decode[LegalEntityList](json) match {
      case Right(data) =>
        for (le <- data.legalEntities) {
          sql"alter table contract auto_increment = ${le.id}".update.apply()
          ContractActions.newContract(date = le.contractDate, pattern_id = 4)
          ContractActions.updateContractTitleAndComment(le.id, le.contractNo)
          ContractActions.updateContractPassword(le.id, le.password)
          ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_LOGIN"), value = le.login)
          ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_SHORT_NAME"), value = le.shortName)
          ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_FULL_NAME"), value = le.fullName)
          le.note1Option.map(x => ContractComment.create(cid = le.id, uid = 0, subject = "Заметка 1", comment = x.trim, dt = DateTime.now, visibled = false))
          le.note2Option.map(x => ContractComment.create(cid = le.id, uid = 0, subject = "Заметка 2", comment = x.trim, dt = DateTime.now, visibled = true))
          le.serviceAddressOption.map(x => ContractActions.updateAddressInfo(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_SERVICE_ADDRESS"), hid = x.buildingId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse(""), room = x.roomOption.getOrElse("")))
          le.legalAddressOption.map(x => ContractActions.updateAddressInfo(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_LEGAL_ADDRESS"), hid = x.buildingId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse(""), room = x.roomOption.getOrElse("")))
          le.billingAddressOption.map(x => ContractActions.updateAddressInfo(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_BILLING_ADDRESS"), hid = x.buildingId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse(""), room = x.roomOption.getOrElse("")))
          le.phoneOption.map(x => ContractActions.updatePhoneInfo(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_PHONE"), phone = x))
          le.emailOption.map(x => ContractActions.updateEmailInfo(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_EMAIL"), email = x))
          le.notificationOption.map { notification =>
            notification.notificationPhoneOption.map(x => ContractActions.updatePhoneInfo(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFICATION_PHONE"), phone = x))
            notification.notificationEmailOption.map(x => ContractActions.updateEmailInfo(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFICATION_EMAIL"), email = x))
            notification.notifyByPhone.map(x => ContractActions.updateParameterType5(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFY_BY_PHONE"), value = x))
            notification.notifyByEmail.map(x => ContractActions.updateParameterType5(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_NOTIFY_BY_EMAIL"), value = x))
          }
          le.bankOption.map { bank =>
            bank.bikOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_BANK_BIK"), value = x))
            bank.nameOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_BANK_NAME"), value = x))
            bank.ksOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_BANK_KS"), value = x))
            bank.rsOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_BANK_RS"), value = x))
          }
          le.stateEntityOption.map(x => ContractActions.updateParameterType5(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_STATE_ENTITY"), value = x))
          le.codesOption.map { codes =>
            codes.innOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_INN"), value = x))
            codes.kppOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_KPP"), value = x))
            codes.ogrnOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_OGRN"), value = x))
            codes.okatoCodeOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_OKATO_CODE"), value = x))
            codes.oktmoCodeOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_OKTMO_CODE"), value = x))
            codes.okvedCodesOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_OKVED_CODES"), value = x))
            codes.okpoCodeOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_OKPO_CODE"), value = x))
          }
          le.chiefPositionOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_CHIEF_POSITION"), value = x))
          le.chiefNameOption.map(x => ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_CHIEF_NAME"), value = x))
          ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_PORTING_PRICE"), value = le.portingPriceOption.getOrElse("0"))
          le.domainIdOption.map { x =>
            val responseFuture = contractService.contractDomainUpdate(le.id, x)
            Await.result(responseFuture, 15.seconds)
          }

          groovyBinding.setProperty("id", le.id)
          val accountNumber = accountNumberGenerator.run()
          ContractActions.updateParameterType1(cid = le.id, pid = config.getInt("CONTRACT_PARAMETER_ACCOUNT_NUMBER"), value = accountNumber.toString)
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
