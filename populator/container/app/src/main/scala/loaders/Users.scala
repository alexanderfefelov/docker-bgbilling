package loaders

import better.files.Resource
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

case class UserData(users: Seq[User])
case class User(id: Int, login: String, password: String)

object Users {

  def load(): Unit = {
    def md5Hash(text: String) : String = java.security.MessageDigest.getInstance("MD5").digest(text.getBytes("UTF-8")).map(0xff & _).map { "%02x".format(_) }.foldLeft(""){_ + _}
    val json = Resource.getAsString("loaders/users.json")
    decode[UserData](json) match {
      case Right(data) =>
        for (u <- data.users) {
          sql"alter table user auto_increment = ${u.id}".update.apply()
          sql"""insert into user (login, name, email, descr, pswd, gr, config, crm_user_id) values (${u.login}, ${u.login}, "", "", ${md5Hash(u.password).toUpperCase}, 0, "", 0)""".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

  sql"alter table user auto_increment = 1000".update.apply()

}
