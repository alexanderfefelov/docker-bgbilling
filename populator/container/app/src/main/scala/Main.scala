import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.db.util.Db
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._
import scalaxb.DispatchHttpClientsAsync

object Main extends App {

  Db.init()

  dynamicCodeRecompile()
  moduleConfigs()
  addresses()
  contractPaymentTypes()
  contractParametersPrefs()
  contractParameterGroups()
  сontractParameterType7Values()
  entitySpecs()
  modulesAndServices()
  Inet.deviceTypes()
  Inet.deviceGroups()
  Inet.vlanResources()
  Inet.trafficTypes()
  Inet.options()
  Inet.devices()
  Inet.deviceReload()

  println("Finished. Press Ctrl+C")

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Автоматизация -> Управление динамическим кодом -> Скомпилировать всё
  //
  private def dynamicCodeRecompile() = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class DynamicCodeServiceCake extends DynamicCodeServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("DynamicCodeService"))
    }
    val dynamicCodeServiceCake = new DynamicCodeServiceCake
    val dynamicCode = dynamicCodeServiceCake.service

    val responseFuture = dynamicCode.recompileAll()
    Await.result(responseFuture, 5.minutes)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Настройка -> Конфигурация
  // Модули -> МОДУЛЬ -> Конфигурация модуля
  //
  private def moduleConfigs() = {
    ModuleConfig.create(mid = Some(0), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1), config = Some(
      """
        |# Конфигурация ядра
        |
        |# Форматы адресов
        |addrs.format=(${city})(, ${street}),(д. ${house})(${frac})(, кв. ${flat})(, п. ${pod})(, э. ${floor})
        |addrs.format.pattern.Обычный=(${city})(, ${street})(, д. ${house})(${frac})(, кв. ${flat})(, п. ${pod})(, э. ${floor})(, ${comment})
        |addrs.format.list=Обычный
        |
      """.stripMargin))
    ModuleConfig.create(mid = Some(1), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1), config = Some(
      """
        |# Конфигурация модуля inet
        |
        |# ID типа устройства, являющегося (корневым) InetAccounting-сервером. Параметр обязателен!
        |accounting.deviceTypeIds=3
        |
      """.stripMargin))
    ModuleConfig.create(mid = Some(2), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1), config = Some(
      """
        |# Конфигурация модуля npay
        |
        |# Автоматическое переначисление абонентских плат договора при изменении их периода, количества, закрытие договора и т. п.
        |# 0 - выключить переначисление, 1 - включить переначисление, 2 - включить переначисление, но выполнять только для текущего месяца
        |recalculate.on.service.change=1
        |
      """.stripMargin))
    ModuleConfig.create(mid = Some(3), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1), config = Some(
      """
        |# Конфигурация модуля rscm
        |
        |# Начисление денег сразу по добавлению услуги в договор
        |hot.calc=1
        |
      """.stripMargin))
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Адреса
  //
  private def addresses() = {
    val countryId = AddressCountry.create(title = "РФ").id
    val cityId = AddressCity.create(countryId = countryId, title = "г. Звенигород").id
    val streetId = AddressStreet.create(cityid = cityId, title = "ул. Мира", pIndex = "143180").id
    val houseId = AddressHouse.create(streetid = streetId, house = 1, frac = Some("Б"), amount = 128, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Типы платежей
  //
  private def contractPaymentTypes() = {
    ContractPaymentTypes.create(title = "Наличные", up = 0, `type` = 0, flag = 0)
    ContractPaymentTypes.create(title = "Банковская карта (офлайн)", up = 0, `type` = 0, flag = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - параметры
  //
  private def contractParametersPrefs() = {
    // Физ. лица
    //
    ContractParametersPref.create(pt = 2, title = "Адрес подключения", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 1, title = "Адрес регистрации", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 9, title = "Телефон моб.", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 9, title = "Телефон дом.", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 3, title = "Email", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 1, title = "Данные паспорта", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 6, title = "Дата рождения", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 1, title = "Место рождения", sort = 1, script = "", flags = 1, lm = DateTime.now())
    // Юр. лица
    //
    ContractParametersPref.create(pt = 1, title = "ИНН", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 1, title = "Телефон(ы)", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 1, title = "Адрес юридический", sort = 1, script = "", flags = 1, lm = DateTime.now())
    ContractParametersPref.create(pt = 7, title = "Организационно-правовая форма", sort = 1, script = "", flags = 1, lm = DateTime.now())
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - группы параметров
  //
  private def contractParameterGroups() = {
    ContractParameterGroupName.create("Физ. лицо")
    ContractParameterGroup.create(gid = 1, pid = 1)
    ContractParameterGroup.create(gid = 1, pid = 2)
    ContractParameterGroup.create(gid = 1, pid = 3)
    ContractParameterGroup.create(gid = 1, pid = 4)
    ContractParameterGroup.create(gid = 1, pid = 5)
    ContractParameterGroup.create(gid = 1, pid = 6)
    ContractParameterGroup.create(gid = 1, pid = 7)
    ContractParameterGroup.create(gid = 1, pid = 8)
    ContractParameterGroupName.create("Юр. лицо")
    ContractParameterGroup.create(gid = 2, pid = 1)
    ContractParameterGroup.create(gid = 2, pid = 9)
    ContractParameterGroup.create(gid = 2, pid = 12)
    ContractParameterGroup.create(gid = 2, pid = 10)
    ContractParameterGroup.create(gid = 2, pid = 11)
    ContractParameterGroup.create(gid = 2, pid = 5)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - значения списков -> Значения списков
  //
  private def сontractParameterType7Values() = {
    ContractParameterType7Values.create(pid = 12, title = "ООО")
    ContractParameterType7Values.create(pid = 12, title = "ЗАО")
    ContractParameterType7Values.create(pid = 12, title = "ПАО")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Атрибуты -> Атрибуты
  // Справочники -> Атрибуты -> Сущности
  //
  private def entitySpecs() = {
    EntitySpecAttr.create(title = "Адрес", `type` = 8, comment = "")
    EntitySpecAttr.create(title = "S/N", `type` = 1, comment = "")
    EntitySpec.create(title = "Коммутатор", entityspectypeid = 0, comment = "", hidden = 0, entitytitlemacros = "")
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 1, pos = 0)
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 2, pos = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  private def modulesAndServices() = {
    import com.github.alexanderfefelov.bgbilling.api.db.repository.{Service => DbService}
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class ModuleServiceCake extends ModuleServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("ModuleService"))
    }
    val moduleServiceCake = new ModuleServiceCake
    val moduleService = moduleServiceCake.service

    val inetModuleIdFuture = moduleService.moduleAdd(Some("inet"), Some("Интернет"))
    val inetModuleId = Await.result(inetModuleIdFuture, 2.minutes)
    DbService.create("Доступ в интернет", mid = inetModuleId, parentid = 0, datefrom = None, dateto = None, comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 30000)

    val npayModuleIdFuture = moduleService.moduleAdd(Some("npay"), Some("Периодические услуги"))
    val npayModuleId = Await.result(npayModuleIdFuture, 60.seconds)
    DbService.create("Абонентская плата", mid = npayModuleId, parentid = 0, datefrom = None, dateto = None, comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)

    val rscmModuleIdFuture = moduleService.moduleAdd(Some("rscm"), Some("Разовые услуги"))
    val rscmModuleId = Await.result(rscmModuleIdFuture, 60.seconds)
    DbService.create("Подключение", mid = rscmModuleId, parentid = 0, datefrom = None, dateto = None, comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
  }

}
