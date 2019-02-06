import java.io.File
import java.nio.charset.Charset

import groovy.lang.{Binding, GroovyShell}
import io.circe.Decoder
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import scalikejdbc._

import scala.util.control.NonFatal

package object loaders {

  implicit def boolean2Int(b:Boolean): Int = if (b) 1 else 0

  // Account number generator
  //
  val groovyBinding = new Binding()
  val groovyShell = new GroovyShell(groovyBinding)
  val accountNumberGenerator = groovyShell.parse(new File("""groovy/AccountNumberGenerator.groovy"""))

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
