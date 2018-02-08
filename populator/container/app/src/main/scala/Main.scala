import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.db.util.Db
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._
import scalaxb.DispatchHttpClientsAsync

object Main extends App {

  Db.init()

  addresses()
  contractPaymentTypes()
  modulesAndServices()
  inetDeviceTypes()

  println("Finished")

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
    InetDeviceType1.create(title = "Access + Accounting", configid = 0, config = "", protocolhandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringProtocolHandler"), sahandlerclass = Some("com.github.alexanderfefelov.bgbilling.device.murmuring.MurmuringServiceActivator"), devicemanagerclass = None, uniqueinterfaces = 0, scriptid = 0, sascript = None, eventscript = None, comment = "", source = None, deviceentityspecid = 0)
  }

}
