package loaders

import better.files.Resource
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

case class ChargeTypeData(chargeTypes: Seq[ChargeType])
case class ChargeType(id: Int, title: String, parent: Int, kind: Int, flag: Int, payback: Boolean)

object ChargeTypes {

  sql"alter table contract_charge_types auto_increment = 1000".update.apply()

  def load(): Unit = {
    val json = Resource.getAsString("loaders/chargeTypes.json")
    decode[ChargeTypeData](json) match {
      case Right(data) =>
        for (ct <- data.chargeTypes) {
          sql"""insert into contract_charge_types (id, title, up, type, flag, payback) values (${ct.id}, ${ct.title}, ${ct.parent}, ${ct.kind}, ${ct.flag}, ${ct.payback})""".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
