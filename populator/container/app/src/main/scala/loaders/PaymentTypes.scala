package loaders

import better.files.Resource
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

case class PaymentTypeData(paymentTypes: Seq[PaymentType])
case class PaymentType(id: Int, title: String, parent: Int, kind: Int, flag: Int)

object PaymentTypes {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/paymentTypes.json")
    decode[PaymentTypeData](json) match {
      case Right(data) =>
        for (pt <- data.paymentTypes) {
          sql"alter table contract_payment_types auto_increment = ${pt.id}".update.apply()
          sql"""insert into contract_payment_types (title, up, type, flag) values (${pt.title}, ${pt.parent}, ${pt.kind}, ${pt.flag})""".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

  sql"alter table contract_payment_types auto_increment = 200".update.apply()

}
