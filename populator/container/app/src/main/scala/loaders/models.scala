package loaders

import org.joda.time.DateTime

case class AddressData(countries: Seq[Country])
case class Country(id: Int, name: String, cities: Seq[City])
case class City(id: Int, name: String, streets: Seq[Street])
case class Street(id: Int, name: String, houses: Seq[House])
case class House(id: Int, number: String, captureDateOption: Option[DateTime])

case class ChargeTypeData(chargeTypes: Seq[ChargeType])
case class ChargeType(id: Int, title: String, parent: Int, kind: Int, flag: Int, payback: Boolean)

case class ContractParameterType7ValueData(contractParameterType7Values: Seq[ContractParameterType7Value])
case class ContractParameterType7Value(id: Int, title: String, parent: Int)

case class ServiceAddress(
  houseId: Int,
  entranceOption: Option[Int],
  floorOption: Option[Int],
  doorOption: Option[String]
)

case class NotificationParameters(
  notificationPhoneOption: Option[String],
  notificationEmailOption: Option[String],
  notifyByPhone: Option[Boolean],
  notifyByEmail: Option[Boolean]
)

case class LegalEntityData(legalEntities: Seq[LegalEntity])
case class LegalEntity(
  id: Int,
  contractNo: String,
  contractDate: DateTime,
  name: String,
  budgetUnit: Boolean,
  login: String,
  password: String,
  note1Option: Option[String],
  note2Option: Option[String]
)

case class IdCard(
  typ: Int,
  seriesOption: Option[String],
  no: String,
  date: DateTime,
  deptCodeOption: Option[String],
  deptName: String
)
case class NaturalPersonData(naturalPersons: Seq[NaturalPerson])
case class NaturalPerson(
  id: Int,
  contractNo: String,
  contractDate: DateTime,
  firstName: String,
  lastName: String,
  middleNameOption: Option[String],
  login: String,
  password: String,
  note1Option: Option[String],
  note2Option: Option[String],
  idCardOption: Option[IdCard],
  birthDateOption: Option[DateTime],
  birthPlaceOption: Option[String],
  serviceAddressOption: Option[ServiceAddress],
  legalAddressIdOption: Option[Int],
  phoneOption: Option[String],
  emailOption: Option[String],
  notificationParameters: NotificationParameters
)

case class PaymentData(payments: Seq[Payment])
case class Payment(typeId: Int, contractId: Int, date: DateTime, amount: Double, comment: String, operatorId: Int)

case class PaymentTypeData(paymentTypes: Seq[PaymentType])
case class PaymentType(id: Int, title: String, parent: Int, kind: Int, flag: Int)

case class UserData(users: Seq[User])
case class User(id: Int, login: String, password: String)
