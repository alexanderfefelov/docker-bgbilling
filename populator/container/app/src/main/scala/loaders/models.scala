package loaders

import org.joda.time.DateTime

case class CountryList(countries: Seq[Country])
case class Country(
  id: Int,
  name: String
)

case class CityList(cities: Seq[City])
case class City(
  countryId: Int,
  id: Int,
  name: String
)

case class StreetList(streets: Seq[Street])
case class Street(
  cityId: Int,
  id: Int,
  name: String
)

case class HouseList(houses: Seq[House])
case class House(
  streetId: Int,
  id: Int,
  number: String,
  postalCodeOption: Option[String],
  captureDateOption: Option[DateTime]
)

case class ChargeTypeList(chargeTypes: Seq[ChargeType])
case class ChargeType(
  id: Int,
  title: String,
  parent: Int,
  kind: Int,
  flag: Int,
  payback: Boolean
)

case class ContractParameterType7ValueList(contractParameterType7Values: Seq[ContractParameterType7Value])
case class ContractParameterType7Value(
  id: Int,
  title: String,
  parent: Int
)

case class Address(
  houseId: Int,
  doorOption: Option[String],
  entranceOption: Option[Int],
  floorOption: Option[Int],
  roomOption: Option[String]
)

import loaders.{Address => ServiceAddress}
import loaders.{Address => LegalAddress}
import loaders.{Address => BillingAddress}

case class Notification(
  notificationPhoneOption: Option[String],
  notificationEmailOption: Option[String],
  notifyByPhone: Option[Boolean],
  notifyByEmail: Option[Boolean]
)

case class Bank(
  bikOption: Option[String],
  nameOption: Option[String],
  ksOption: Option[String],
  rsOption: Option[String]
)

case class Codes(
  innOption: Option[String],
  kppOption: Option[String],
  ogrnOption: Option[String],
  okatoOption: Option[String],
  oktmoOption: Option[String],
  okvedOption: Option[String],
  okpoOption: Option[String]
)

case class LegalEntityList(legalEntities: Seq[LegalEntity])
case class LegalEntity(
  id: Int,
  contractNo: String,
  contractDate: DateTime,
  name: String,
  login: String,
  password: String,
  note1Option: Option[String],
  note2Option: Option[String],
  serviceAddressOption: Option[ServiceAddress],
  legalAddressOption: Option[LegalAddress],
  billingAddressOption: Option[BillingAddress],
  codesOption: Option[Codes],
  stateEntityOption: Option[Boolean],
  phoneOption: Option[String],
  emailOption: Option[String],
  notificationOption: Option[Notification],
  bankOption: Option[Bank],
  portingPriceOption: Option[String],
  domainIdOption: Option[Int]
)

case class IdCard(
  typ: Int,
  seriesOption: Option[String],
  number: String,
  date: DateTime,
  deptCodeOption: Option[String],
  deptName: String
)

case class NaturalPersonList(naturalPersons: Seq[NaturalPerson])
case class NaturalPerson(
  id: Int,
  contractNo: String,
  contractDate: DateTime,
  firstName: String,
  lastName: String,
  middleNameOption: Option[String],
  sexOption: Option[Int],
  login: String,
  password: String,
  note1Option: Option[String],
  note2Option: Option[String],
  idCardOption: Option[IdCard],
  birthDateOption: Option[DateTime],
  birthPlaceOption: Option[String],
  serviceAddressOption: Option[ServiceAddress],
  legalAddressOption: Option[LegalAddress],
  phoneOption: Option[String],
  emailOption: Option[String],
  notificationOption: Option[Notification],
  portingPriceOption: Option[String],
  domainIdOption: Option[Int]
)

case class PaymentList(payments: Seq[Payment])
case class Payment(
  typeId: Int,
  contractId: Int,
  date: DateTime,
  amount: Double,
  comment: String,
  operatorId: Int
)

case class PaymentTypeList(paymentTypes: Seq[PaymentType])
case class PaymentType(
  id: Int,
  title: String,
  parent: Int,
  kind: Int,
  flag: Int
)

case class UserList(users: Seq[User])
case class User(
  id: Int,
  login: String,
  password: String
)

case class DomainList(domains: Seq[Domain])
case class Domain(
  id: Int,
  name: String,
  description: String
)
