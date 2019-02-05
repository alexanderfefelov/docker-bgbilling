package loaders

import better.files.Resource
import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import io.circe.generic.auto._
import io.circe.parser._
import modules.dr
import scalaxb.{ConfigurableDispatchHttpClientsAsync, Soap11ClientsWithAuthHeaderAsync}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Payments {

  def load(): Unit = {
    class PaymentCake extends PaymentServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("payment-service"))
    }
    val paymentService = new PaymentCake().service

    val dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZZ"

    val json = Resource.getAsString("loaders/payments.json")
    decode[PaymentData](json) match {
      case Right(data) =>
        for (p <- data.payments) {
          val payment = com.github.alexanderfefelov.bgbilling.api.soap.kernel.Payment(comment = Some(p.comment), attributes = Map(
            "id" ->         dr("id", -1),
            "contractId" -> dr("contractId", p.contractId),
            "date" ->       dr("date", p.date.toString(dateTimeFormat)),
            "sum" ->        dr("sum", p.amount),
            "summa" ->      dr("summa", p.amount),
            "typeId" ->     dr("typeId", p.typeId),
            "userId" ->     dr("userId", p.operatorId)
          ))
          val responseFuture = paymentService.paymentUpdate(Some(payment), None)
          Await.result(responseFuture, 15.seconds)
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
