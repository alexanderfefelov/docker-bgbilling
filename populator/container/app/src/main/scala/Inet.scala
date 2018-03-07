import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.soap.inet._
import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import scalaxb.{DataRecord, DispatchHttpClientsAsync}

import scala.concurrent.Await
import scala.concurrent.duration._

object Inet {

  private class InetDeviceServiceCake extends InetDeviceServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with ApiSoapConfig {
    override def baseAddress = new java.net.URI(soapServiceBaseAddress("InetDeviceService"))
  }
  private val inetDeviceService = new InetDeviceServiceCake().service

  private def dr[A](key: String, value: A) = DataRecord(Some(""), Some(key), value)

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Типы устройств
  //
  def deviceTypes() = {
    InetDeviceType1.create(title = "Network", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    InetDeviceType1.create(title = "SP-VLAN", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    InetDeviceType1.create(title = "Access + Accounting", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)

    var deviceTypeId = InetDeviceType1.create(title = "MikroTik CRS125-24G-1S-RM", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.qinq.QinqProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = Some("com.github.alexanderfefelov.bgbilling.device.mikrotik.RouterOsDeviceManager"), uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"ether$i", deviceTypeId)
    }
    InetInterface1.create(25, s"sfp1", deviceTypeId)

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-3120-24SC B1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 A1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }
    for (i <- 25 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DES-3200-28 C1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 24) {
      InetInterface1.create(i, s"$i", deviceTypeId)
    }
    for (i <- 25 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }

    deviceTypeId = InetDeviceType1.create(title = "D-Link DGS-1210-28/ME B1", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 1).id
    for (i <- 1 to 28) {
      InetInterface1.create(i, s"ge$i", deviceTypeId)
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Группы устройств
  //
  def deviceGroups() = {
    def create(title: String) = InvDeviceGroup1.create(parentid = 0, title = title, cityid = 0, comment = "")
    create("Сервер доступа")
    create("Коммутатор агрегации")
    create("Коммутатор доступа")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> VLAN-ресурсы
  //
  def vlanResources() = {
    def create(title: String, vlanFrom: Int, vlanTo: Int) = {
      InvVlanCategory1.create(parentid = 0, title = title)
      InvVlanResource1.create(title = title, vlanfrom = vlanFrom, vlanto = vlanTo, comment = "", categoryid = 1)
    }
    create("0800", 100, 3500)
    create("0900", 100, 3500)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> IP-ресурсы
  //
  def ipResources() = {
    InvIpCategory1.create(parentid = 0, title = "Динамические серые адреса")
    InvIpResource1.create(categoryid = 1, addressfrom = Array[Byte](192.toByte, 168.toByte, 50, 10), addressto = Array[Byte](192.toByte, 168.toByte, 50, 12), router = "192.168.50.1", subnetmask = "255.255.255.0", dns = "192.168.50.1", config = "", comment = "")
    InvIpResource1.create(categoryid = 1, addressfrom = Array[Byte](192.toByte, 168.toByte, 51, 10), addressto = Array[Byte](192.toByte, 168.toByte, 51, 12), router = "192.168.51.1", subnetmask = "255.255.255.0", dns = "192.168.51.1", config = "", comment = "")
    InvIpCategory1.create(parentid = 0, title = "Динамические белые адреса")
    InvIpResource1.create(categoryid = 1, addressfrom = Array[Byte](192.toByte, 168.toByte, 60, 10), addressto = Array[Byte](192.toByte, 168.toByte, 60, 12), router = "192.168.60.1", subnetmask = "255.255.255.0", dns = "192.168.60.1", config = "", comment = "")
    InvIpResource1.create(categoryid = 1, addressfrom = Array[Byte](192.toByte, 168.toByte, 61, 10), addressto = Array[Byte](192.toByte, 168.toByte, 61, 12), router = "192.168.61.1", subnetmask = "255.255.255.0", dns = "192.168.61.1", config = "", comment = "")
    InvIpCategory1.create(parentid = 0, title = "Статические белые адреса")
    InvIpResource1.create(categoryid = 2, addressfrom = Array[Byte](192.toByte, 168.toByte, 70, 10), addressto = Array[Byte](192.toByte, 168.toByte, 70, 12), router = "192.168.70.1", subnetmask = "255.255.255.0", dns = "192.168.70.1", config = "", comment = "")
    InvIpResource1.create(categoryid = 2, addressfrom = Array[Byte](192.toByte, 168.toByte, 71, 10), addressto = Array[Byte](192.toByte, 168.toByte, 71, 12), router = "192.168.71.1", subnetmask = "255.255.255.0", dns = "192.168.71.1", config = "", comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Трафик -> Типы трафика
  //
  def trafficTypes() = {
    def create(title: String) = InetTrafficType1.create(title = title, unit = 30000)
    create("Входящий трафик")
    create("Исходящий трафик")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Трафик -> Привязка типов трафика
  //
  def trafficTypeLinks() = {
    InetTrafficTypeLink1.create("NetFlow")
    InetTrafficTypeLinkRule1.create(1, 0, None, None, 2, 0, -1, 1, Some(Array[Byte](192.toByte, 168.toByte, 0, 1)), Some(Array[Byte](192.toByte, 168.toByte, 255.toByte, 255.toByte)), 0, 0, None, "", "", -1, -1, None, 2, "")
    InetTrafficTypeLinkRule1.create(1, 0, None, None, 2, 0, -1, 2, Some(Array[Byte](192.toByte, 168.toByte, 0, 1)), Some(Array[Byte](192.toByte, 168.toByte, 255.toByte, 255.toByte)), 0, 0, None, "", "", -1, -1, None, 1, "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Опции
  //
  def options() = {
    def create(parentId: Int, title: String) = InetOption1.create(parentid = parentId, title = title, groupintersection = 0, config = "", comment = "")
    create(0, "Скорость")
    create(1, "50 Мбит/с")
    create(1, "100 Мбит/с")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Типы сервисов
  //
  def servTypes() = {
    var cfg =
      """
        |ip.resource.categoryId=1
        |title.pattern=Динамический серый адрес, VLAN ${vlan}
      """.stripMargin
    InetServType1.create(title = "Динамический серый адрес", config = Some(cfg), parenttypeids = "", sessioninitiationtype = 0, sessioncountlimit = 1, sessioncountlimitlock = 1, addresstype = 4, addressallinterface = 1, traffictypelinkid = 0, needlogin = 0, needdevice = 1, needinterface = 1, personalinterface = 1, needvlan = 1, needidentifier = 0, needmacaddress = 0, needcontractobject = 0, needrestriction = 0, personalvlan = 1)
    cfg =
      """
        |ip.resource.categoryId=2
        |title.pattern=Динамический белый адрес, VLAN ${vlan}
        |      """.stripMargin
    InetServType1.create(title = "Динамический белый адрес", config = Some(cfg), parenttypeids = "", sessioninitiationtype = 0, sessioncountlimit = 1, sessioncountlimitlock = 1, addresstype = 4, addressallinterface = 1, traffictypelinkid = 0, needlogin = 0, needdevice = 1, needinterface = 1, personalinterface = 1, needvlan = 1, needidentifier = 0, needmacaddress = 0, needcontractobject = 0, needrestriction = 0, personalvlan = 1)
    cfg =
      """
        |title.pattern=Статический белый адрес ${addressIp}, VLAN ${vlan}
      """.stripMargin
    InetServType1.create(title = "Статический белый адрес", config = Some(cfg), parenttypeids = "", sessioninitiationtype = 0, sessioncountlimit = 1, sessioncountlimitlock = 1, addresstype = 3, addressallinterface = 1, traffictypelinkid = 0, needlogin = 0, needdevice = 1, needinterface = 1, personalinterface = 1, needvlan = 1, needidentifier = 0, needmacaddress = 0, needcontractobject = 0, needrestriction = 0, personalvlan = 1)
    InetServTypeDeviceGroupLink1.create(inetservid = 1, devicegroupid = 3)
    InetServTypeDeviceGroupLink1.create(inetservid = 2, devicegroupid = 3)
    InetServTypeDeviceGroupLink1.create(inetservid = 2, devicegroupid = 3)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Библиотека
  // Модули -> Интернет -> Устройства и ресурсы -> Дерево
  //
  def devices() = {
    var invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 1),
      "ident" ->        dr("ident", "Моя сеть"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    var responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val rootId = Await.result(responseFuture, 15.seconds)
    var device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None, username = None, invConfig = None, attributes = Map(
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

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 3),
      "ident" ->        dr("ident", "Access + Accounting"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val aaId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None, username = None, invConfig = None, attributes = Map(
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
        |# Пулы для сервисов с типом адреса "статический адрес". Пулы для сервисов
        |# с типом адреса "динамический адрес" указываются в конфигурации сервисов.
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
        |# Регулярное выражение для извлечения SP-VID и C-VID из Option 82 Agent Remote ID Sub-option.
        |qinq.vlansRegex=.*s(\d\d\d\d)c(\d\d\d\d).*
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some("192.168.99.1:8728"), uptime = None, uptimeTime = None, username = Some("api"), attributes = Map(
      "parentId" ->       dr("parentId", 0),
      "deviceTypeId" ->   dr("deviceTypeId", 4),
      "deviceGroupIds" -> dr("deviceGroupIds", "1"),
      "ident" ->          dr("ident", "192.168.99.1"),
      "password" ->       dr("password", "api"),
      "secret" ->         dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val mtId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None, username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", aaInvId),
      "deviceTypeId" ->    dr("deviceTypeId", 4),
      "ident" ->           dr("ident", "192.168.99.1"),
      "password" ->        dr("password", "api"),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", mtId),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", "192.168.99.1"),
      "invHost" ->         dr("invHost", "192.168.99.1:8728")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val mtInvId = Await.result(responseFuture, 15.seconds)

    cfg =
      """
        |qinq.spvid=0800
        |vlan.resource.category=1
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 2),
      "ident" ->        dr("ident", "0800"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val sp800Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None, username = None, invConfig = None, attributes = Map(
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
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     dr("parentId", 0),
      "deviceTypeId" -> dr("deviceTypeId", 2),
      "ident" ->        dr("ident", "0900"),
      "password" ->     dr("password", ""),
      "secret" ->       dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val sp900Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None, username = None, invConfig = None, attributes = Map(
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

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("10.0.0.22:23"), uptime = None, uptimeTime = None, username = Some("admin"), attributes = Map(
      "parentId" ->       dr("parentId", 0),
      "deviceTypeId" ->   dr("deviceTypeId", 5),
      "deviceGroupIds" -> dr("deviceGroupIds", "2"),
      "ident" ->          dr("ident", "10.0.0.22"),
      "password" ->       dr("password", "password"),
      "secret" ->         dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val dlink3120Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None, username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", sp800InvId),
      "deviceTypeId" ->    dr("deviceTypeId", 5),
      "ident" ->           dr("ident", "10.0.0.22"),
      "password" ->        dr("password", "password"),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", dlink3120Id),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", "10.0.0.22"),
      "invHost" ->         dr("invHost", "10.0.0.22:23")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val dlink3120InvId = Await.result(responseFuture, 15.seconds)

    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(""), host = Some("10.0.0.32:23"), uptime = None, uptimeTime = None, username = Some("admin"), attributes = Map(
      "parentId" ->       dr("parentId", 0),
      "deviceTypeId" ->   dr("deviceTypeId", 6),
      "deviceGroupIds" -> dr("deviceGroupIds", "3"),
      "ident" ->          dr("ident", "10.0.0.32"),
      "password" ->       dr("password", "password"),
      "secret" ->         dr("secret", "")
    ))
    responseFuture = inetDeviceService.deviceUpdate(Some(invDevice))
    val dlink3200a1Id = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = None, config = None, host = None, uptime = None, uptimeTime = None, username = None, invConfig = None, attributes = Map(
      "parentId" ->        dr("parentId", dlink3120InvId),
      "deviceTypeId" ->    dr("deviceTypeId", 6),
      "ident" ->           dr("ident", "10.0.0.32"),
      "password" ->        dr("password", "password"),
      "secret" ->          dr("secret", ""),
      "invDeviceId" ->     dr("invDeviceId", dlink3200a1Id),
      "invDeviceTypeId" -> dr("invDeviceTypeId", 2),
      "invIdent" ->        dr("invIdent", "10.0.0.32"),
      "invHost" ->         dr("invHost", "10.0.0.32:23")
    ))
    responseFuture = inetDeviceService.inetDeviceUpdate(Some(device), false)
    val dlink3200a1InvId = Await.result(responseFuture, 15.seconds)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Дерево -> Перечитать конфигурацию на серверах
  //
  def deviceReload() = {
    val responseFuture = inetDeviceService.deviceReload()
    Await.result(responseFuture, 5.minutes)
  }

}
