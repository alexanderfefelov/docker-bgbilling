import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig

import scala.concurrent.Await
import scala.concurrent.duration._
import scalaxb.DispatchHttpClientsAsync

object Inet {

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
    InvDeviceGroup1.create(parentid = 0, title = "Сервер доступа", cityid = 0, comment = "")
    InvDeviceGroup1.create(parentid = 0, title = "Коммутатор агрегации", cityid = 0, comment = "")
    InvDeviceGroup1.create(parentid = 0, title = "Коммутатор доступа", cityid = 0, comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> VLAN-ресурсы
  //
  def vlanResources() = {
    InvVlanCategory1.create(parentid = 0, title = "0800")
    InvVlanResource1.create(title = "0800", vlanfrom = 500, vlanto = 4000, comment = "", categoryid = 1)
    InvVlanCategory1.create(parentid = 0, title = "0900")
    InvVlanResource1.create(title = "0900", vlanfrom = 500, vlanto = 4000, comment = "", categoryid = 2)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Справочники -> Трафик -> Типы трафика
  //
  def trafficTypes() = {
    InetTrafficType1.create(title = "Входящий трафик", unit = 30000)
    InetTrafficType1.create(title = "Исходящий трафик", unit = 30000)
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
    InetOption1.create(parentid = 0, title = "Скорость", groupintersection = 0, config = "", comment = "")
    InetOption1.create(parentid = 1, title = "50 Мбит/с", groupintersection = 0, config = "", comment = "")
    InetOption1.create(parentid = 1, title = "100 Мбит/с", groupintersection = 0, config = "", comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Интернет -> Устройства и ресурсы -> Библиотека
  // Модули -> Интернет -> Устройства и ресурсы -> Дерево
  //
  def devices() = {
    import scalaxb._
    import com.github.alexanderfefelov.bgbilling.api.soap.inet._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class InetDeviceServiceCake extends InetDeviceServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("InetDeviceService"))
    }
    val inetDeviceServiceCake = new InetDeviceServiceCake
    val inetDevice = inetDeviceServiceCake.service

    var cfg =
      """
        |
      """.stripMargin
    var invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" -> DataRecord(None, Some("deviceTypeId"), 1),
      "ident" ->        DataRecord(None, Some("ident"), "Моя сеть"),
      "password" ->     DataRecord(None, Some("password"), ""),
      "secret" ->       DataRecord(None, Some("secret"), "")
    ))
    var responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val rootId = Await.result(responseFuture, 15.seconds)
    var device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), invConfig = Some(""), attributes = Map(
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

    cfg =
      """
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" -> DataRecord(None, Some("deviceTypeId"), 3),
      "ident" ->        DataRecord(None, Some("ident"), "Access + Accounting"),
      "password" ->     DataRecord(None, Some("password"), ""),
      "secret" ->       DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val aaId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), invConfig = Some(""), attributes = Map(
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

    cfg =
      """
        |# dhcp.deviceSearchMode
        |# 0 - по giaddr или IP-адресу источника идет поиск устройства, далее у этого устройства
        |# вызывается предобработка preprocessDhcpRequest (где можно при необходимости извлечь и установить
        |# AGENT_REMOTE_ID, а также INTERFACE_ID или VLAN_ID), далее по установленному AGENT_REMOTE_ID или,
        |# если AGENT_REMOTE_ID не установлен - по конфигурации dhcp.option82.agentRemoteId.x agentRemoteId
        |# если AGENT_REMOTE_ID не установлен - по конфигурации dhcp.option82.agentRemoteId.x agentRemoteId
        |# извлекается из пакета и идет поиск агентского устройства по совпадению устройства, далее у идентификатора
        |# агентского устройства, если таковое найдено вызывается preprocessDhcpRequest (где можно при
        |# необходимости извлечь и установить INTERFACE_ID или VLAN_ID).
        |dhcp.deviceSearchMode=0
        |
        |# dhcp.servSearchMode
        |# 4 - поиск по VLAN'у на устройстве и его дочерних устройствах.
        |dhcp.servSearchMode=4
        |
        |# qinq.vlansRegex
        |# Регулярное выражение для извлечения SP-VID и CVID из Option 82 Agent Remote ID Sub-option
        |qinq.vlansRegex=.*s(\d\d\d\d)c(\d\d\d\d)$
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some("192.168.99.1:8728"), uptime = None, uptimeTime = None, username = Some("api"), attributes = Map(
      "parentId" ->       DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" ->   DataRecord(None, Some("deviceTypeId"), 4),
      "deviceGroupIds" -> DataRecord(None, Some("deviceGroupIds"), "1"),
      "ident" ->          DataRecord(None, Some("ident"), "192.168.99.1"),
      "password" ->       DataRecord(None, Some("password"), "api"),
      "secret" ->         DataRecord(None, Some("secret"), "")
    ))
    responseFuture = inetDevice.deviceUpdate(Some(invDevice))
    val mtId = Await.result(responseFuture, 15.seconds)
    device = InetDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some("192.168.99.1:8728"), uptime = None, uptimeTime = None, username = Some("api"), invConfig = Some(""), attributes = Map(
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

    cfg =
      """
        |qinq.spvid=800
        |vlan.resource.category=1
        |
      """.stripMargin
    invDevice = InvDevice(entityAttributes = EntityAttributes(), children = Seq(), comment = Some(""), config = Some(cfg), host = Some(""), uptime = None, uptimeTime = None, username = Some(""), attributes = Map(
      "parentId" ->     DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" -> DataRecord(None, Some("deviceTypeId"), 2),
      "ident" ->        DataRecord(None, Some("ident"), "SP-VLAN 800"),
      "password" ->     DataRecord(None, Some("password"), ""),
      "secret" ->       DataRecord(None, Some("secret"), "")
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
      "parentId" ->     DataRecord(None, Some("parentId"), 0),
      "deviceTypeId" -> DataRecord(None, Some("deviceTypeId"), 2),
      "ident" ->        DataRecord(None, Some("ident"), "SP-VLAN 900"),
      "password" ->     DataRecord(None, Some("password"), ""),
      "secret" ->       DataRecord(None, Some("secret"), "")
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
  def deviceReload() = {
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
