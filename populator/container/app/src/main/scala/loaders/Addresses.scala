package loaders

import better.files.Resource
import com.github.alexanderfefelov.bgbilling.api.action.kernel.AdminActions
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

object Addresses {

  def load(): Unit = {
    val json = Resource.getAsString("loaders/addresses.json")
    decode[AddressData](json) match {
      case Right(data) =>
        for (country <- data.countries) {
          sql"insert into address_country (id, title) values (${country.id}, ${country.name})".update.apply()

          for (city <- country.cities) {
            sql"insert into address_city (id, country_id, title) values (${city.id}, ${country.id}, ${city.name})".update.apply()

            for (street <- city.streets) {
              sql"""insert into address_street (id, cityid, title, p_index) values (${street.id}, ${city.id}, ${street.name}, "")""".update.apply()

              for (house <- street.houses) {
                val r = """^(\d*)(.*)""".r
                house.number match {
                  case r(y, z) => (y, z)
                    sql"""insert into address_house (id, streetid, house, frac, amount, areaid, quarterid, box_index, pod_diapazon, pod) values (${house.id}, ${street.id}, ${toInt(y)}, $z, 0, 0, 0, ${house.postalCodeOption}, "", "")""".update.apply()
                    house.captureDateOption.map(x => AdminActions.updateAddressExtraParams("house", house.id, "captureDate", x.toString("dd.MM.yyyy")))
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
