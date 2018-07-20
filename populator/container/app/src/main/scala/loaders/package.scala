import java.nio.charset.Charset

import io.circe.Decoder
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import scalikejdbc._

import scala.util.control.NonFatal

package object loaders {

  // better-files
  //
  implicit val charset: Charset = Charset.forName("UTF-8")

  // scalikejdbc
  //
  ConnectionPool.singleton("jdbc:mysql://master.mysql.bgbilling.local:3306/bgbilling?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "bill", "bgbilling")
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
