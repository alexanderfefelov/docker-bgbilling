package loaders

import better.files.Resource
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

object PaymentTypes {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/paymentTypes.json")
    decode[PaymentTypeData](json) match {
      case Right(data) =>
        for (pt <- data.paymentTypes) {
          sql"""insert into contract_payment_types (id, title, up, type, flag) values (${pt.id}, ${pt.title}, ${pt.parent}, ${pt.kind}, ${pt.flag})""".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
