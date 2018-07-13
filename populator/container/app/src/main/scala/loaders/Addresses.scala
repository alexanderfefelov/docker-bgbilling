package loaders

import better.files.Resource
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import io.circe.generic.auto._
import io.circe.parser._

case class AddressData(countries: Seq[Country])
case class Country(id: Int, name: String, cities: Seq[City])
case class City(id: Int, name: String, streets: Seq[Street])
case class Street(id: Int, name: String, houses: Seq[House])
case class House(id: Int, number: String)

object Addresses {

  def load(): Unit = {
    val json = Resource.getAsString("loader/addresses.json")
    decode[AddressData](json) match {
      case Right(data) =>
        data.countries.map { country =>
          country.cities.map { city =>
            AddressCity(city.id, country.id, city.name).save()
            city.streets.map { street =>
              AddressStreet(street.id, street.name, "", city.id).save()
              street.houses.map { house =>
                ???
              }
            }
          }
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
  }

}
