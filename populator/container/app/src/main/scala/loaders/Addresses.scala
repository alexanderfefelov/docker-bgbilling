package loaders

import com.github.alexanderfefelov.bgbilling.api.db.repository.AddressConfig
import io.circe.generic.auto._
import io.circe.parser._
import scalikejdbc._

object Addresses {

  def load(json: String): Unit = {
    decode[CountryList](json) match {
      case Right(data) =>
        for (country <- data.countries) {
          sql"insert into address_country (id, title) values (${country.id}, ${country.name})".update.apply()
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
    decode[CityList](json) match {
      case Right(data) =>
        for (city <- data.cities) {
          sql"insert into address_city (id, country_id, title) values (${city.id}, ${city.countryId}, ${city.name})".update.apply()
          city.kladrIdOption.map(x => AddressConfig.create(tableId = "city", recordId = city.id, key = "kladrId", value = x))
          city.fiasIdOption.map(x => AddressConfig.create(tableId = "city", recordId = city.id, key = "fiasId", value = x))
          city.geoLatOption.map(x => AddressConfig.create(tableId = "city", recordId = city.id, key = "geoLat", value = x))
          city.geoLonOption.map(x => AddressConfig.create(tableId = "city", recordId = city.id, key = "geoLon", value = x))
          city.regionOption.map(x => AddressConfig.create(tableId = "city", recordId = city.id, key = "region", value = x))
          city.areaOption.map(x => AddressConfig.create(tableId = "city", recordId = city.id, key = "area", value = x))
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
    decode[StreetList](json) match {
      case Right(data) =>
        for (street <- data.streets) {
          sql"""insert into address_street (id, cityid, title, p_index) values (${street.id}, ${street.cityId}, ${street.name}, "")""".update.apply()
          street.kladrIdOption.map(x => AddressConfig.create(tableId = "street", recordId = street.id, key = "kladrId", value = x))
          street.fiasIdOption.map(x => AddressConfig.create(tableId = "street", recordId = street.id, key = "fiasId", value = x))
        }
      case Left(error) =>
        throw new RuntimeException(error)
    }
    decode[BuildingList](json) match {
      case Right(data) =>
        for (building <- data.buildings) {
          val r = """^(\d*)(.*)""".r
          building.number match {
            case r(y, z) => (y, z)
              sql"""insert into address_house (id, streetid, house, frac, amount, areaid, quarterid, box_index, pod_diapazon, pod) values (${building.id}, ${building.streetId}, ${toInt(y)}, $z, 0, 0, 0, ${building.postalCodeOption}, "", "")""".update.apply()
              building.kladrIdOption.map(x => AddressConfig.create(tableId = "house", recordId = building.id, key = "kladrId", value = x))
              building.fiasIdOption.map(x => AddressConfig.create(tableId = "house", recordId = building.id, key = "fiasId", value = x))
              building.geoLatOption.map(x => AddressConfig.create(tableId = "house", recordId = building.id, key = "geoLat", value = x))
              building.geoLonOption.map(x => AddressConfig.create(tableId = "house", recordId = building.id, key = "geoLons", value = x))
              building.captureDateOption.map(x => AddressConfig.create("house", building.id, "captureDate", x.toString("dd.MM.yyyy")))
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
