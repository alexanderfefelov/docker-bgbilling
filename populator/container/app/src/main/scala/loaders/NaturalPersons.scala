package loaders

import better.files.Resource
import com.github.alexanderfefelov.bgbilling.api.action.kernel.ContractActions
import io.circe.generic.auto._
import io.circe.parser._
import org.joda.time.DateTime
import scalikejdbc._

case class NaturalPersonData(naturalPersons: Seq[NaturalPerson])
case class NaturalPerson(
  id: Int,
  contractNo: String,
  contractDate: DateTime,
  name: String,
  login: String,
  password: String
)

object NaturalPersons {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/naturalPersons.json")
    decode[NaturalPersonData](json) match {
      case Right(data) =>
        for (c <- data.naturalPersons) {
          sql"alter table contract auto_increment = ${c.id}".update.apply()
          ContractActions.newContract(date = c.contractDate, pattern_id = 1)
          ContractActions.updateContractTitleAndComment(c.id, c.contractNo)
          ContractActions.updateContractPassword(c.id, c.password)
          ContractActions.updateParameterType1(cid = c.id, pid = 1, value = c.login)
          val names = c.name.split(" ")
          ContractActions.updateParameterType1(cid = c.id, pid = 6, value = names(0))
          ContractActions.updateParameterType1(cid = c.id, pid = 7, value = names(1))
          ContractActions.updateParameterType1(cid = c.id, pid = 8, value = names(2)) // TODO Отчество не всегда имеется
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

  sql"alter table contract auto_increment = 23456".update.apply()

}
