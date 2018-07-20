package loaders

import better.files.Resource
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

case class AddressData(countries: Seq[Country])
case class Country(id: Int, name: String, cities: Seq[City])
case class City(id: Int, name: String, streets: Seq[Street])
case class Street(id: Int, name: String, houses: Seq[House])
case class House(id: Int, number: String)

object Addresses {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/addresses.json")
    decode[AddressData](json) match {
      case Right(data) =>
        for (country <- data.countries) {
          sql"insert into address_country (id, title) values (${country.id}, ${country.name})".update.apply()

          for (city <-country.cities) {
            sql"insert into address_city (id, country_id, title) values (${city.id}, ${country.id}, ${city.name})".update.apply()

            for (street <- city.streets) {
              sql"""insert into address_street (id, cityid, title, p_index) values (${street.id}, ${city.id}, ${street.name}, "")""".update.apply()

              for (house <-street.houses) {
                val r = """^(\d*)(.*)""".r
                house.number match {
                  case r(y, z) => (y, z)
                    sql"""insert into address_house (id, streetid, house, frac, amount, areaid, quarterid, pod_diapazon, pod) values (${house.id}, ${street.id}, ${toInt(y)}, $z, 0, 0, 0, "", "")""".update.apply()
                }
              }
            }
          }
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

  private def toInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }

}
