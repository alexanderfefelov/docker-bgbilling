package loaders

import better.files.Resource
import com.github.alexanderfefelov.bgbilling.api.action.kernel.ContractActions
import com.github.alexanderfefelov.bgbilling.api.db.repository.ContractComment
import io.circe.generic.auto._
import io.circe.parser._
import org.joda.time.DateTime
import scalikejdbc._

case class LegalEntityData(legalEntities: Seq[LegalEntity])
case class LegalEntity(
  id: Int,
  contractNo: String,
  contractDate: DateTime,
  name: String,
  budgetUnit: Boolean,
  login: String,
  password: String,
  note1Option: Option[String],
  note2Option: Option[String]
)

object LegalEntities {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/legalEntities.json")
    decode[LegalEntityData](json) match {
      case Right(data) =>
        for (c <- data.legalEntities) {
          sql"alter table contract auto_increment = ${c.id}".update.apply()
          ContractActions.newContract(date = c.contractDate, pattern_id = 4)
          ContractActions.updateContractTitleAndComment(c.id, c.contractNo)
          ContractActions.updateContractPassword(c.id, c.password)
          ContractActions.updateParameterType1(cid = c.id, pid = 1, value = c.login)
          ContractActions.updateParameterType1(cid = c.id, pid = 14, value = c.name)
          c.note1Option.map(x => ContractComment.create(cid = c.id, uid = 0, subject = "Заметка 1", comment = x.trim, dt = DateTime.now, visibled = false))
          c.note2Option.map(x => ContractComment.create(cid = c.id, uid = 0, subject = "Заметка 2", comment = x.trim, dt = DateTime.now, visibled = false))
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

  sql"alter table contract auto_increment = 23456".update.apply()

}
