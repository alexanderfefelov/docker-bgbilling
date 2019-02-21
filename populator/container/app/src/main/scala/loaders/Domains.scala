package loaders

import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

object Domains {

  def load(json: String): Unit = {
    decode[DomainData](json) match {
      case Right(data) =>
        for (d <- data.domains) {
          sql"""insert into domain (id, parentid, title, comment) values (${d.id}, 0, ${d.name}, ${d.description})""".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
