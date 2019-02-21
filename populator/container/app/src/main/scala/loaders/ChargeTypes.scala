package loaders

import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

object ChargeTypes {

  def load(json: String): Unit = {
    decode[ChargeTypeList](json) match {
      case Right(data) =>
        for (ct <- data.chargeTypes) {
          sql"""insert into contract_charge_types (id, title, up, type, flag, payback) values (${ct.id}, ${ct.title}, ${ct.parent}, ${ct.kind}, ${ct.flag}, ${ct.payback})""".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
