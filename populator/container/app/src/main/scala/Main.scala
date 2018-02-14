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
  modulesAndServices()
  inetDeviceTypes()
  inetDeviceGroups()
  inetVlanResources()
  inetTrafficTypes()
  inetOptions()
  devices()
  deviceReload()

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
    ContractParameterGroup.create(1, 2)
    ContractParameterGroup.create(1, 3)
    ContractParameterGroup.create(1, 4)
    ContractParameterGroup.create(1, 5)
    ContractParameterGroup.create(1, 6)
    ContractParameterGroup.create(1, 7)
    ContractParameterGroup.create(1, 8)
    ContractParameterGroupName.create("Юр. лицо")
    ContractParameterGroup.create(gid = 2, pid = 1)
    ContractParameterGroup.create(2, 9)
    ContractParameterGroup.create(2, 12)
    ContractParameterGroup.create(2, 10)
    ContractParameterGroup.create(2, 11)
    ContractParameterGroup.create(2, 5)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - значения списков -> Значения списков
  //
  private def сontractParameterType7Values() = {
    ContractParameterType7Values.create(pid = 12, title = "ООО")
    ContractParameterType7Values.create(12, "ЗАО")
    ContractParameterType7Values.create(12, "ПАО")
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
    InetDeviceType1.create(title = "Network", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    InetDeviceType1.create(title = "SP-VLAN", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    InetDeviceType1.create(title = "Access + Accounting", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    var deviceTypeId = InetDeviceType1.create(title = "MikroTik CRS125-24G-1S-RM", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = Some("com.github.alexanderfefelov.bgbilling.device.mikrotik.RouterOsDeviceManager"), uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"ether$i", deviceTypeId)
    }
    InetInterface1.create(25, s"sfp1", deviceTypeId)

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-3120-24SC B1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 A1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }
    for (i <- 25 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 C1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }
    for (i <- 25 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-1210-28/ME B1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0).id
    for (i <- 1 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
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
  // Модули -> Интернет -> Устройства и ресурсы -> VLAN-ресурсы
  //
  private def inetVlanResources() = {
    InvVlanCategory1.create(parentid = 0, title = "0800")
    InvVlanResource1.create(title = "0800", vlanfrom = 500, vlanto = 4000, comment = "", categoryid = 1)
    InvVlanCategory1.create(parentid = 0, title = "0900")
    InvVlanResource1.create(title = "0900", vlanfrom = 500, vlanto = 4000, comment = "", categoryid = 2)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Трафик -> Типы трафика
  //
  private def inetTrafficTypes() = {
    InetTrafficType1.create(title = "Входящий трафик", unit = 30000)
    InetTrafficType1.create(title = "Исходящий трафик", unit = 30000)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Опции
  //
  private def inetOptions() = {
    InetOption1.create(parentid = 0, title = "Скорость", groupintersection = 0, config = "", comment = "")
    InetOption1.create(parentid = 1, title = "50 Мбит/с", groupintersection = 0, config = "", comment = "")
    InetOption1.create(parentid = 1, title = "100 Мбит/с", groupintersection = 0, config = "", comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Библиотека
  // Модули -> Интернет -> Устройства и ресурсы -> Дерево
  //
  private def devices() = {
    import scalaxb._
    import com.github.alexanderfefelov.bgbilling.api.soap.inet._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class InetDeviceServiceCake extends InetDeviceServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("InetDeviceService"))
    }
    val inetDeviceServiceCake = new InetDeviceServiceCake
    val inetDevice = inetDeviceServiceCake.service

    var invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" -> DataRecord(None, Some("deviceTypeId"), 1),
      "ident" ->        DataRecord(None, Some("ident"), "Моя сеть"),
      "password" ->     DataRecord(None, Some("password"), ""),
      "secret" ->       DataRecord(None, Some("secret"), "")
    ))
    var responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val rootId = Await.result(responseFuture, 15.seconds)
    var device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), invConfig = Some(""), attributes = Map(
      "parentId" ->        DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" ->    DataRecord(None, Some("deviceTypeId"), 1),
      "ident" ->           DataRecord(None, Some("ident"), "Моя сеть"),
      "password" ->        DataRecord(None, Some("password"), ""),
      "secret" ->          DataRecord(None, Some("secret"), ""),
      "invDeviceId" ->     DataRecord(None, Some("invDeviceId"), rootId),
      "invDeviceTypeId" -> DataRecord(None, Some("invDeviceTypeId"), 0),
      "invIdent" ->        DataRecord(None, Some("invIdent"), "Моя сеть"),
      "invHost" ->         DataRecord(None, Some("invHost"), "")
    ))
    responseFuture = inetDevice.inetDeviceUpdate(Some(device), false)
    val rootInvId = Await.result(responseFuture, 15.seconds)

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" -> DataRecord(None, Some("deviceTypeId"), 3),
      "ident" ->        DataRecord(None, Some("ident"), "Access + Accounting"),
      "password" ->     DataRecord(None, Some("password"), ""),
      "secret" ->       DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val aaId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), invConfig = Some(""), attributes = Map(
      "parentId" ->        DataRecord(None, Some("parentId"), rootInvId),
      "deviceTypeId" ->    DataRecord(None, Some("deviceTypeId"), 3),
      "ident" ->           DataRecord(None, Some("ident"), "Access + Accounting"),
      "password" ->        DataRecord(None, Some("password"), ""),
      "secret" ->          DataRecord(None, Some("secret"), ""),
      "invDeviceId" ->     DataRecord(None, Some("invDeviceId"), aaId),
      "invDeviceTypeId" -> DataRecord(None, Some("invDeviceTypeId"), 1),
      "invIdent" ->        DataRecord(None, Some("invIdent"), "Access + Accounting"),
      "invHost" ->         DataRecord(None, Some("invHost"), "")
    ))
    responseFuture = inetDevice.inetDeviceUpdate(Some(device), false)
    val aaInvId = Await.result(responseFuture, 15.seconds)

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("192.168.99.1:8728"), uptime = None, uptimeTime = None, username = Some("api"), attributes = Map(
      "parentId" ->       DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" ->   DataRecord(None, Some("deviceTypeId"), 4),
      "deviceGroupIds" -> DataRecord(None, Some("deviceGroupIds"), "1"),
      "ident" ->          DataRecord(None, Some("ident"), "192.168.99.1"),
      "password" ->       DataRecord(None, Some("password"), "api"),
      "secret" ->         DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val mtId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("192.168.99.1:8728"), uptime = None, uptimeTime = None, username = Some("api"), invConfig = Some(""), attributes = Map(
      "parentId" ->        DataRecord(None, Some("parentId"), aaInvId),
      "deviceTypeId" ->    DataRecord(None, Some("deviceTypeId"), 4),
      "ident" ->           DataRecord(None, Some("ident"), "192.168.99.1"),
      "password" ->        DataRecord(None, Some("password"), "api"),
      "secret" ->          DataRecord(None, Some("secret"), ""),
      "invDeviceId" ->     DataRecord(None, Some("invDeviceId"), mtId),
      "invDeviceTypeId" -> DataRecord(None, Some("invDeviceTypeId"), 2),
      "invIdent" ->        DataRecord(None, Some("invIdent"), "192.168.99.1"),
      "invHost" ->         DataRecord(None, Some("invHost"), "192.168.99.1:8728")
    ))
    responseFuture = inetDevice.inetDeviceUpdate(Some(device), false)
    val mtInvId = Await.result(responseFuture, 15.seconds)

    var cfg =
      """
        |qinq.spvid=800
        |vlan.resource.category=1
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->       DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" ->   DataRecord(None, Some("deviceTypeId"), 2),
      "ident" ->          DataRecord(None, Some("ident"), "SP-VLAN 800"),
      "password" ->       DataRecord(None, Some("password"), ""),
      "secret" ->         DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val sp800Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), invConfig = Some(""), attributes = Map(
      "parentId" ->        DataRecord(None, Some("parentId"), mtInvId),
      "deviceTypeId" ->    DataRecord(None, Some("deviceTypeId"), 2),
      "ident" ->           DataRecord(None, Some("ident"), "SP-VLAN 800"),
      "password" ->        DataRecord(None, Some("password"), ""),
      "secret" ->          DataRecord(None, Some("secret"), ""),
      "invDeviceId" ->     DataRecord(None, Some("invDeviceId"), sp800Id),
      "invDeviceTypeId" -> DataRecord(None, Some("invDeviceTypeId"), 2),
      "invIdent" ->        DataRecord(None, Some("invIdent"), "SP-VLAN 800"),
      "invHost" ->         DataRecord(None, Some("invHost"), "")
    ))
    responseFuture = inetDevice.inetDeviceUpdate(Some(device), false)
    val sp800InvId = Await.result(responseFuture, 15.seconds)

    cfg =
      """
        |qinq.spvid=900
        |vlan.resource.category=2
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->       DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" ->   DataRecord(None, Some("deviceTypeId"), 2),
      "ident" ->          DataRecord(None, Some("ident"), "SP-VLAN 900"),
      "password" ->       DataRecord(None, Some("password"), ""),
      "secret" ->         DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val sp900Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), invConfig = Some(""), attributes = Map(
      "parentId" ->        DataRecord(None, Some("parentId"), mtInvId),
      "deviceTypeId" ->    DataRecord(None, Some("deviceTypeId"), 2),
      "ident" ->           DataRecord(None, Some("ident"), "SP-VLAN 900"),
      "password" ->        DataRecord(None, Some("password"), ""),
      "secret" ->          DataRecord(None, Some("secret"), ""),
      "invDeviceId" ->     DataRecord(None, Some("invDeviceId"), sp900Id),
      "invDeviceTypeId" -> DataRecord(None, Some("invDeviceTypeId"), 2),
      "invIdent" ->        DataRecord(None, Some("invIdent"), "SP-VLAN 900"),
      "invHost" ->         DataRecord(None, Some("invHost"), "")
    ))
    responseFuture = inetDevice.inetDeviceUpdate(Some(device), false)
    val sp900InvId = Await.result(responseFuture, 15.seconds)

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("10.0.0.22:23"), uptime = None, uptimeTime = None, username = Some("admin"), attributes = Map(
      "parentId" ->       DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" ->   DataRecord(None, Some("deviceTypeId"), 5),
      "deviceGroupIds" -> DataRecord(None, Some("deviceGroupIds"), "2"),
      "ident" ->          DataRecord(None, Some("ident"), "10.0.0.22"),
      "password" ->       DataRecord(None, Some("password"), "password"),
      "secret" ->         DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val dlink3120Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("10.0.0.22:23"), uptime = None, uptimeTime = None, username = Some("admin"), invConfig = Some(""), attributes = Map(
      "parentId" ->        DataRecord(None, Some("parentId"), sp800InvId),
      "deviceTypeId" ->    DataRecord(None, Some("deviceTypeId"), 5),
      "ident" ->           DataRecord(None, Some("ident"), "10.0.0.22"),
      "password" ->        DataRecord(None, Some("password"), "password"),
      "secret" ->          DataRecord(None, Some("secret"), ""),
      "invDeviceId" ->     DataRecord(None, Some("invDeviceId"), dlink3120Id),
      "invDeviceTypeId" -> DataRecord(None, Some("invDeviceTypeId"), 2),
      "invIdent" ->        DataRecord(None, Some("invIdent"), "10.0.0.22"),
      "invHost" ->         DataRecord(None, Some("invHost"), "10.0.0.22:23")
    ))
    responseFuture = inetDevice.inetDeviceUpdate(Some(device), false)
    val dlink3120InvId = Await.result(responseFuture, 15.seconds)

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("10.0.0.32:23"), uptime = None, uptimeTime = None, username = Some("admin"), attributes = Map(
      "parentId" ->       DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" ->   DataRecord(None, Some("deviceTypeId"), 6),
      "deviceGroupIds" -> DataRecord(None, Some("deviceGroupIds"), "3"),
      "ident" ->          DataRecord(None, Some("ident"), "10.0.0.32"),
      "password" ->       DataRecord(None, Some("password"), "password"),
      "secret" ->         DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val dlink3200a1Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("10.0.0.32:23"), uptime = None, uptimeTime = None, username = Some("admin"), invConfig = Some(""), attributes = Map(
      "parentId" ->        DataRecord(None, Some("parentId"), dlink3120InvId),
      "deviceTypeId" ->    DataRecord(None, Some("deviceTypeId"), 6),
      "ident" ->           DataRecord(None, Some("ident"), "10.0.0.32"),
      "password" ->        DataRecord(None, Some("password"), "password"),
      "secret" ->          DataRecord(None, Some("secret"), ""),
      "invDeviceId" ->     DataRecord(None, Some("invDeviceId"), dlink3200a1Id),
      "invDeviceTypeId" -> DataRecord(None, Some("invDeviceTypeId"), 2),
      "invIdent" ->        DataRecord(None, Some("invIdent"), "10.0.0.32"),
      "invHost" ->         DataRecord(None, Some("invHost"), "10.0.0.32:23")
    ))
    responseFuture = inetDevice.inetDeviceUpdate(Some(device), false)
    val dlink3200a1InvId = Await.result(responseFuture, 15.seconds)
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
