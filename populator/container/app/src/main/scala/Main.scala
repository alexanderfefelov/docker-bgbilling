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
  modulesAndServices()
  inetDeviceTypes()
  inetDeviceGroups()
  deviceReload()

  println("Finished")

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
      """.stripMargin))
    ModuleConfig.create(mid = Some(1), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1), config = Some(
      """
        |# Конфигурация модуля inet
        |
      """.stripMargin))
    ModuleConfig.create(mid = Some(2), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1), config = Some(
      """
        |# Конфигурация модуля npay
        |
      """.stripMargin))
    ModuleConfig.create(mid = Some(3), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1), config = Some(
      """
        |# Конфигурация модуля rscm
        |
      """.stripMargin))
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Адреса
  //
  private def addresses() = {
    val countryId = AddressCountry.create("РФ").id
    val cityId = AddressCity.create(countryId, "г. Звенигород").id
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

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Типы устройств
  //
  private def inetDeviceTypes() = {
    InetDeviceType1.create(title = "Root Device", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    var deviceTypeId = InetDeviceType1.create(title = "MikroTik CRS125-24G-1S-RM", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = Some("com.github.alexanderfefelov.bgbilling.device.mikrotik.RouterOsDeviceManager"), uniqueinterfaces = 1, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"ether$i", deviceTypeId)
    }
    InetInterface1.create(25, s"sfp1", deviceTypeId)

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-3120-24SC B1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 1, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 A1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 1, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 28) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 C1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 1, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 28) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-1210-28/ME C1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 1, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 28) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Группы устройств
  //
  private def inetDeviceGroups() = {
    InvDeviceGroup1.create(parentid = 0, title = "Сервер доступа", cityid = 0, comment = "")
    InvDeviceGroup1.create(parentid = 0, title = "Коммутатор агрегации", cityid = 0, comment = "")
    InvDeviceGroup1.create(parentid = 0, title = "Коммутатор доступа", cityid = 0, comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Дерево -> Перечитать конфигурацию на серверах
  //
  private def deviceReload() = {
    import com.github.alexanderfefelov.bgbilling.api.soap.inet._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class InetDeviceServiceCake extends InetDeviceServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("InetDeviceService"))
    }
    val inetDeviceServiceCake = new InetDeviceServiceCake
    val inetDevice = inetDeviceServiceCake.service

    val responseFuture = inetDevice.deviceReload()
    Await.result(responseFuture, 5.minutes)
  }

}
