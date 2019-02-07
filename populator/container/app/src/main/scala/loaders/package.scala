import java.io.File
import java.nio.charset.Charset

import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import groovy.lang.{Binding, GroovyShell}
import io.circe.Decoder
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import scalaxb.{ConfigurableDispatchHttpClientsAsync, Soap11ClientsWithAuthHeaderAsync}
import scalikejdbc._

import scala.util.control.NonFatal

package object loaders {

  implicit def boolean2Int(b:Boolean): Int = if (b) 1 else 0

  class ContractCake extends ContractServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
    override def baseAddress = new java.net.URI(soapServiceBaseAddress("payment-service"))
  }
  val contractService = new ContractCake().service

  // Account number generator
  //
  val groovyBinding = new Binding()
  val groovyShell = new GroovyShell(groovyBinding)
  val accountNumberGenerator = groovyShell.parse(new File("src/main/groovy/AccountNumberGenerator.groovy"))

  // better-files
  //
  implicit val charset: Charset = Charset.forName("UTF-8")

  // scalikejdbc
  //
  ConnectionPool.singleton(url = "jdbc:mysql://master.mysql.bgbilling.local:3306/bgbilling?useUnicode=true&characterEncoding=UTF-8&useSSL=false",
    user = "bill", password = "bgbilling")
  implicit val session = AutoSession

  // Joda-Time
  //
  val dateFormatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd")

  // circe
  //
  implicit val decodeDateTime: Decoder[DateTime] = Decoder.decodeString.emap { s =>
    try {
      Right(DateTime.parse(s, dateFormatter))
    } catch {
      case NonFatal(e) => Left(e.getMessage)
    }
  }

}
