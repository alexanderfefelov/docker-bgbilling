import java.nio.charset.Charset

import scalikejdbc._

package object loaders {

  implicit val charset: Charset = Charset.forName("UTF-8")

  ConnectionPool.singleton("jdbc:mysql://master.mysql.bgbilling.local:3306/bgbilling?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "bill", "bgbilling")

  implicit val session = AutoSession

}
