package loaders

import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

object Users {

  def load(json: String): Unit = {
    def md5Hash(text: String) : String = java.security.MessageDigest.getInstance("MD5").digest(text.getBytes("UTF-8")).map(0xff & _).map { "%02x".format(_) }.foldLeft(""){_ + _}

   decode[UserList](json) match {
      case Right(data) =>
        for (u <- data.users) {
          sql"""insert into user (id, login, name, email, descr, pswd, gr, config, crm_user_id) values (${u.id}, ${u.login}, ${u.login}, "", "", ${md5Hash(u.password).toUpperCase}, 0, "", 0)""".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
