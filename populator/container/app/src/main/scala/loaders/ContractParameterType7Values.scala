package loaders

import better.files.Resource
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

case class ContractParameterType7ValueData(contractParameterType7Values: Seq[ContractParameterType7Value])
case class ContractParameterType7Value(id: Int, title: String, parent: Int)

object ContractParameterType7Values {

  sql"alter table contract_parameter_type_7_values auto_increment = 1000".update.apply()

  def load(): Unit = {
    val json = Resource.getAsString("loaders/contractParameterType7Values.json")
    decode[ContractParameterType7ValueData](json) match {
      case Right(data) =>
        for (v <- data.contractParameterType7Values) {
          sql"insert into contract_parameter_type_7_values (id, title, pid) values (${v.id}, ${v.title}, ${v.parent})".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
