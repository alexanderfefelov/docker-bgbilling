package loaders

import better.files.Resource
import com.github.alexanderfefelov.bgbilling.api.action.kernel.ContractActions
import com.github.alexanderfefelov.bgbilling.api.db.repository.ContractComment
import io.circe.generic.auto._
import io.circe.parser._
import org.joda.time.DateTime
import scalikejdbc._

object LegalEntities {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/legalEntities.json")
    decode[LegalEntityData](json) match {
      case Right(data) =>
        for (le <- data.legalEntities) {
          sql"alter table contract auto_increment = ${le.id}".update.apply()
          ContractActions.newContract(date = le.contractDate, pattern_id = 4)
          ContractActions.updateContractTitleAndComment(le.id, le.contractNo)
          ContractActions.updateContractPassword(le.id, le.password)
          ContractActions.updateParameterType1(cid = le.id, pid = 1, value = le.login)
          ContractActions.updateParameterType1(cid = le.id, pid = 14, value = le.name)
          le.note1Option.map(x => ContractComment.create(cid = le.id, uid = 0, subject = "Заметка 1", comment = x.trim, dt = DateTime.now, visibled = false))
          le.note2Option.map(x => ContractComment.create(cid = le.id, uid = 0, subject = "Заметка 2", comment = x.trim, dt = DateTime.now, visibled = true))
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
