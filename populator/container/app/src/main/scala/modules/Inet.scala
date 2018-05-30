package modules

import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.soap.inet._
import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService
import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import org.joda.time.DateTime
import scalaxb.{DataRecord, DispatchHttpClientsAsync}

import scala.concurrent.Await
import scala.concurrent.duration._

object Inet {

  private class InetDeviceServiceCake extends InetDeviceServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
    override def baseAddress = new java.net.URI(soapServiceBaseAddress("inet-device-service"))
  }
  private val inetDeviceService = new InetDeviceServiceCake().service

  private def addr(a1: Int, a2: Int, a3: Int, a4: Int) = Array[Byte](a1.toByte, a2.toByte, a3.toByte, a4.toByte)

  private def dr(key: String, value: String) = DataRecord(None, Some(key), value)
  private def dr(key: String, value: Int) = DataRecord(None, Some(key), value)

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    import com.github.alexanderfefelov.bgbilling.api.db.repository.{Service => DbService}

    val moduleIdFuture = moduleService.moduleAdd(Some("inet"), Some("Интернет"))
    val moduleId = Await.result(moduleIdFuture, 10.minutes)
    DbService.create("Доступ в интернет", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 30000)
    moduleId
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Типы устройств
  //
  def deviceTypes(): Unit = {
    val murmuringProtocolHandler = "com.github.alexanderfefelov.bgbilling.dyn.device.murmuring.MurmuringProtocolHandler"
    val murmuringServiceActivator = "com.github.alexanderfefelov.bgbilling.dyn.device.murmuring.MurmuringServiceActivator"

    cmt =
      """
        |Псевдоустройство этого типа должно являться единственным корнем дерева устройств.
        |
        |Назначение - распространение каких-то глобальных параметров конфигурации по всем устройствам.
        |""".stripMargin
    InetDeviceType1.create(title = "Network", configid = 0, config = "",
      protocolhandlerclass = Some(murmuringProtocolHandler),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = None,
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = cmt, source = None, deviceentityspecid = 0)

    cmt =
      """
        |Идентификатор псевдоустройств этого типа используется при dhcp.deviceSearchMode=0 и dhcp.servSearchMode=4.
        |
        |Идентификатор не случаен, это дополненное при необходимости нулями слева четырехзначное
        |значение SP-VID, извлекаемое из DHCP Relay Agent Information Option.
        |""".stripMargin
    InetDeviceType1.create(title = "SP-VLAN", configid = 0, config = "",
      protocolhandlerclass = Some(murmuringProtocolHandler),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = None,
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = cmt, source = None, deviceentityspecid = 0)

    InetDeviceType1.create(title = "Access + Accounting", configid = 0, config = "",
      protocolhandlerclass = Some(murmuringProtocolHandler),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = None,
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    var deviceTypeId = InetDeviceType1.create(title = "MikroTik CRS125-24G-1S-RM", configid = 0, config = "",
      protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.dyn.device.qinq.QinqProtocolHandler"),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = Some("com.github.alexanderfefelov.bgbilling.dyn.device.mikrotik.RouterOsDeviceManager"),
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"ether$i", deviceTypeId)
    }
    InetInterface1.create(25, s"sfp1", deviceTypeId)

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-3120-24SC B1", configid = 0, config = "",
      protocolhandlerclass = Some(murmuringProtocolHandler),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = None,
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 A1", configid = 0, config = "",
      protocolhandlerclass = Some(murmuringProtocolHandler),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = None,
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }
    for (i <- 25 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 C1", configid = 0, config = "",
      protocolhandlerclass = Some(murmuringProtocolHandler),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = None,
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }
    for (i <- 25 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-1210-28/ME B1", configid = 0, config = "",
      protocolhandlerclass = Some(murmuringProtocolHandler),
      sahandlerclass = Some(murmuringServiceActivator),
      devicemanagerclass = None,
      uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Группы устройств
  //
  def deviceGroups(): Unit = {
    def create(title: String) = InvDeviceGroup1.create(parentid = 0, title = title, cityid = 0, comment = "")
    create("Сервер доступа")
    create("Коммутатор агрегации")
    create("Коммутатор доступа")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> VLAN-ресурсы
  //
  def vlanResources(): Unit = {
    def create(title: String, vlanFrom: Int, vlanTo: Int) = {
      val id = InvVlanCategory1.create(parentid = 0, title = title).id
      InvVlanResource1.create(title = title, vlanfrom = vlanFrom, vlanto = vlanTo, comment = "", categoryid = id)
    }
    create("0800", 100, 3500)
    create("0900", 100, 3500)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> IP-ресурсы
  //
  def ipResources(): Unit = {
    InvIpCategory1.create(parentid = 0, title = "Динамические серые адреса")
    InvIpResource1.create(categoryid = 1, addressfrom = addr(192, 168, 50, 10), addressto = addr(192, 168, 50, 12), router = "192.168.50.1", subnetmask = "255.255.255.0", dns = "192.168.50.1", config = "", comment = "", dynamic = Some(false))
    InvIpResource1.create(categoryid = 1, addressfrom = addr(192, 168, 51, 10), addressto = addr(192, 168, 51, 12), router = "192.168.51.1", subnetmask = "255.255.255.0", dns = "192.168.51.1", config = "", comment = "", dynamic = Some(false))
    InvIpCategory1.create(parentid = 0, title = "Динамические белые адреса")
    InvIpResource1.create(categoryid = 2, addressfrom = addr(192, 168, 60, 10), addressto = addr(192, 168, 60, 12), router = "192.168.60.1", subnetmask = "255.255.255.0", dns = "192.168.60.1", config = "", comment = "", dynamic = Some(false))
    InvIpResource1.create(categoryid = 2, addressfrom = addr(192, 168, 61, 10), addressto = addr(192, 168, 61, 12), router = "192.168.61.1", subnetmask = "255.255.255.0", dns = "192.168.61.1", config = "", comment = "", dynamic = Some(false))
    InvIpCategory1.create(parentid = 0, title = "Статические белые адреса")
    InvIpResource1.create(categoryid = 3, addressfrom = addr(192, 168, 70, 10), addressto = addr(192, 168, 70, 12), router = "192.168.70.1", subnetmask = "255.255.255.0", dns = "192.168.70.1", config = "", comment = "", dynamic = Some(false))
    InvIpResource1.create(categoryid = 3, addressfrom = addr(192, 168, 71, 10), addressto = addr(192, 168, 71, 12), router = "192.168.71.1", subnetmask = "255.255.255.0", dns = "192.168.71.1", config = "", comment = "", dynamic = Some(false))
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Трафик -> Типы трафика
  //
  def trafficTypes(): Unit = {
    def create(title: String) = InetTrafficType1.create(title = title, unit = 30000)
    create("Входящий трафик")
    create("Исходящий трафик")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Трафик -> Привязка типов трафика
  //
  def trafficTypeLinks(): Unit = {
    InetTrafficTypeLink1.create("NetFlow")
    InetTrafficTypeLinkRule1.create(linkid = 1, position = 0, datefrom = None, dateto = None, `type` = 2, sourceid = 0, interfaceid = -1, direction = 1, addressfrom = Some(addr(192, 168, 0, 1)), addressto = Some(addr(192, 168, 255, 255)), portfrom = 0, portto = 0, diffserv = None, counterrealm = "", counterservice = "", countervendor = -1, countertype = -1, counterprefix = None, traffictypeid = 2, comment = "")
    InetTrafficTypeLinkRule1.create(linkid = 1, position = 0, datefrom = None, dateto = None, `type` = 2, sourceid = 0, interfaceid = -1, direction = 2, addressfrom = Some(addr(192, 168, 0, 1)), addressto = Some(addr(192, 168, 255, 255)), portfrom = 0, portto = 0, diffserv = None, counterrealm = "", counterservice = "", countervendor = -1, countertype = -1, counterprefix = None, traffictypeid = 1, comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Опции
  //
  def options(): Unit = {
    def create(parentId: Int, title: String) = InetOption1.create(parentid = parentId, title = title, groupintersection = 0, config = "", comment = "")
    create(0, "Скорость")
    create(1, "50 Мбит/с")
    create(1, "100 Мбит/с")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Типы сервисов
  //
  def servTypes(): Unit = {
    def create(title: String, config: String, addressType: Byte) = {
      val id = InetServType1.create(title = title, config = Some(config), parenttypeids = "", sessioninitiationtype = 0, sessioncountlimit = 1, sessioncountlimitlock = 1, addresstype = addressType, addressallinterface = 1, traffictypelinkid = 0, needlogin = 0, needdevice = 1, needinterface = 1, personalinterface = 1, needvlan = 1, needidentifier = 0, needmacaddress = 0, needcontractobject = 0, needrestriction = 0, personalvlan = 1).id
      InetServTypeDeviceGroupLink1.create(inetservid = id, devicegroupid = 3)
    }
    var cfg =
      """
        |ip.resource.categoryId=1
        |title.pattern=Динамический серый адрес, VLAN (${vlan})
        |""".stripMargin
    create("Динамический серый адрес", cfg, 4)
    cfg =
      """
        |ip.resource.categoryId=2
        |title.pattern=Динамический белый адрес, VLAN (${vlan})
        |""".stripMargin
    create("Динамический белый адрес", cfg, 4)
    cfg =
      """
        |title.pattern=Статический белый адрес (${addressIp}), VLAN (${vlan})
        |""".stripMargin
    create("Статический белый адрес", cfg, 3)

    cfg =
      """
        |title.pattern=Порт Ethernet, VLAN (${vlan})
        |""".stripMargin
    val id = InetServType1.create(title = "Порт Ethernet", config = Some(cfg), parenttypeids = "", sessioninitiationtype = 0, sessioncountlimit = 1, sessioncountlimitlock = 1, addresstype = 0, addressallinterface = 1, traffictypelinkid = 0, needlogin = 0, needdevice = 1, needinterface = 1, personalinterface = 1, needvlan = 1, needidentifier = 0, needmacaddress = 0, needcontractobject = 0, needrestriction = 0, personalvlan = 0).id
    InetServTypeDeviceGroupLink1.create(inetservid = id, devicegroupid = 3)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Библиотека
  // Модули -> Интернет -> Устройства и ресурсы -> Дерево
  //
  def devices(): Unit = {
    var invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None,
      username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 1),
      "ident" ->        dr("ident", "Моя сеть"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    var responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val rootId = Await.result(responseFuture, 15.seconds)
    var device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None,
      username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", 0),
      "deviceTypeId" ->    dr("deviceTypeId", 1),
      "ident" ->           dr("ident", "Моя сеть"),
      "password" ->        dr("password", ""),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", rootId),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 0),
      "invIdent" ->        dr("invIdent", "Моя сеть"),
      "invHost" ->         dr("invHost", "")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val rootInvId = Await.result(responseFuture, 15.seconds)

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None,
      username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 3),
      "ident" ->        dr("ident", "Access + Accounting"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val aaId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None,
      username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", rootInvId),
      "deviceTypeId" ->    dr("deviceTypeId", 3),
      "ident" ->           dr("ident", "Access + Accounting"),
      "password" ->        dr("password", ""),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", aaId),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 1),
      "invIdent" ->        dr("invIdent", "Access + Accounting"),
      "invHost" ->         dr("invHost", "")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val aaInvId = Await.result(responseFuture, 15.seconds)

    var cfg =
      """
        |dhcp.serverIdentifier=192.168.99.254
        |
        |dhcp.option.leaseTime=300
        |dhcp.option.renewalTime=150
        |dhcp.option.rebindingTime=250
        |
        |# Пулы для сервисов с типом адреса "статический адрес".
        |ip.resource.categoryId=3
        |
        |# dhcp.deviceSearchMode
        |# 0 - по giaddr или IP-адресу источника идет поиск устройства, далее у этого устройства
        |# вызывается предобработка preprocessDhcpRequest (где можно при необходимости извлечь и установить
        |# AGENT_REMOTE_ID, а также INTERFACE_ID или VLAN_ID), далее по установленному AGENT_REMOTE_ID или,
        |# если AGENT_REMOTE_ID не установлен - по конфигурации dhcp.option82.agentRemoteId.x agentRemoteId
        |# извлекается из пакета и идет поиск агентского устройства по совпадению идентификатора устройства,
        |# далее у агентского устройства, если таковое найдено, вызывается preprocessDhcpRequest (где можно
        |# при необходимости извлечь и установить INTERFACE_ID или VLAN_ID).
        |dhcp.deviceSearchMode=0
        |
        |# dhcp.servSearchMode
        |# 4 - поиск по VLAN'у на устройстве и его дочерних устройствах.
        |dhcp.servSearchMode=4
        |
        |# qinq.vlansRegex
        |# Регулярное выражение для извлечения SP-VID и C-VID из DHCP Relay Agent Information Option.
        |qinq.vlansRegex=.*s(\d\d\d\d)c(\d\d\d\d).*
        |""".stripMargin
    var host = "192.168.99.1"
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(s"$host:8728"), uptime = None, uptimeTime = None,
      username = Some("api"), attributes = Map(
      "parentId" ->       dr("parentId", 0),
      "deviceTypeId" ->   dr("deviceTypeId", 4),
      "deviceGroupIds" -> dr("deviceGroupIds", "1"),
      "ident" ->          dr("ident", host),
      "password" ->       dr("password", "api"),
      "secret" ->         dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val mtId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None,
      username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", aaInvId),
      "deviceTypeId" ->    dr("deviceTypeId", 4),
      "ident" ->           dr("ident", host),
      "password" ->        dr("password", "api"),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", mtId),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", host),
      "invHost" ->         dr("invHost", s"$host:8728")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val mtInvId = Await.result(responseFuture, 15.seconds)

    cfg =
      """
        |qinq.spvid=0800
        |vlan.resource.category=1
        |""".stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None,
      username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 2),
      "ident" ->        dr("ident", "0800"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val sp800Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None,
      username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", mtInvId),
      "deviceTypeId" ->    dr("deviceTypeId", 2),
      "ident" ->           dr("ident", "0800"),
      "password" ->        dr("password", ""),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", sp800Id),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", "0800"),
      "invHost" ->         dr("invHost", "")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val sp800InvId = Await.result(responseFuture, 15.seconds)

    cfg =
      """
        |qinq.spvid=0900
        |vlan.resource.category=2
        |""".stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None,
      username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 2),
      "ident" ->        dr("ident", "0900"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val sp900Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None,
      username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", mtInvId),
      "deviceTypeId" ->    dr("deviceTypeId", 2),
      "ident" ->           dr("ident", "0900"),
      "password" ->        dr("password", ""),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", sp900Id),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", "0900"),
      "invHost" ->         dr("invHost", "")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val sp900InvId = Await.result(responseFuture, 15.seconds)

    host = "10.0.0.22"
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(s"$host:23"), uptime = None, uptimeTime = None,
      username = Some("admin"), attributes = Map(
      "parentId" ->       dr("parentId", 0),
      "deviceTypeId" ->   dr("deviceTypeId", 5),
      "deviceGroupIds" -> dr("deviceGroupIds", "2"),
      "ident" ->          dr("ident", host),
      "password" ->       dr("password", "password"),
      "secret" ->         dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val dlink3120Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None,
      username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", sp800InvId),
      "deviceTypeId" ->    dr("deviceTypeId", 5),
      "ident" ->           dr("ident", host),
      "password" ->        dr("password", "password"),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", dlink3120Id),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", host),
      "invHost" ->         dr("invHost", s"$host:23")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val dlink3120InvId = Await.result(responseFuture, 15.seconds)

    host = "10.0.0.32"
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(s"$host:23"), uptime = None, uptimeTime = None,
      username = Some("admin"), attributes = Map(
      "parentId" ->       dr("parentId", 0),
      "deviceTypeId" ->   dr("deviceTypeId", 6),
      "deviceGroupIds" -> dr("deviceGroupIds", "3"),
      "ident" ->          dr("ident", host),
      "password" ->       dr("password", "password"),
      "secret" ->         dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val dlink3200a1Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None,
      username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", dlink3120InvId),
      "deviceTypeId" ->    dr("deviceTypeId", 6),
      "ident" ->           dr("ident", host),
      "password" ->        dr("password", "password"),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", dlink3200a1Id),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", host),
      "invHost" ->         dr("invHost", s"$host:23")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val dlink3200a1InvId = Await.result(responseFuture, 15.seconds)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Дерево -> Перечитать конфигурацию на серверах
  //
  def deviceReload(): Unit = {
    val responseFuture = inetDeviceService.deviceReload()
    Await.result(responseFuture, 5.minutes)
  }

}
