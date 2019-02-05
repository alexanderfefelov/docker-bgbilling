package loaders

import better.files.Resource
import com.github.alexanderfefelov.bgbilling.api.action.kernel.ContractActions
import com.github.alexanderfefelov.bgbilling.api.db.repository.ContractComment
import io.circe.generic.auto._
import io.circe.parser._
import org.joda.time.DateTime
import scalikejdbc._

object NaturalPersons {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/naturalPersons.json")
    decode[NaturalPersonData](json) match {
      case Right(data) =>
        for (np <- data.naturalPersons) {
          sql"alter table contract auto_increment = ${np.id}".update.apply()
          ContractActions.newContract(date = np.contractDate, pattern_id = 1)
          ContractActions.updateContractTitleAndComment(np.id, np.contractNo)
          ContractActions.updateContractPassword(np.id, np.password)
          ContractActions.updateParameterType1(cid = np.id, pid = 1, value = np.login)
          ContractActions.updateParameterType1(cid = np.id, pid = 6, value = np.lastName)
          ContractActions.updateParameterType1(cid = np.id, pid = 7, value = np.firstName)
          np.middleNameOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 8, value = x))
          np.note1Option.map(x => ContractComment.create(cid = np.id, uid = 0, subject = "Заметка 1", comment = x.trim, dt = DateTime.now, visibled = false))
          np.note2Option.map(x => ContractComment.create(cid = np.id, uid = 0, subject = "Заметка 2", comment = x.trim, dt = DateTime.now, visibled = true))
          np.idCardOption.map { idCard =>
            ContractActions.updateListParameter(cid = np.id, pid = 9, value = idCard.typ)
            idCard.seriesOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 10, value = x))
            ContractActions.updateParameterType1(cid = np.id, pid = 11, value = idCard.no)
            ContractActions.updateParameterType6(cid = np.id, pid = 12, value = idCard.date)
            idCard.deptCodeOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 13, value = x))
            ContractActions.updateParameterType1(cid = np.id, pid = 14, value = idCard.deptName)
          }
          np.birthDateOption.map(x => ContractActions.updateParameterType6(cid = np.id, pid = 16, value = x))
          np.birthPlaceOption.map(x => ContractActions.updateParameterType1(cid = np.id, pid = 17, value = x))
          np.serviceAddressOption.map(x => ContractActions.updateAddressInfo(cid = np.id, pid = 3, hid = x.houseId, pod = x.entranceOption.getOrElse(0), floor = x.floorOption.getOrElse(0), flat = x.doorOption.getOrElse("")))
          np.legalAddressIdOption.map(x => ContractActions.updateAddressInfo(cid = np.id, pid = 15, hid = x, pod = 0, floor = 0, flat = ""))
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
