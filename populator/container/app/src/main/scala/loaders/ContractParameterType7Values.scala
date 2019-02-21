package loaders

import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

object ContractParameterType7Values {

  def load(json: String): Unit = {
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
