import java.nio.charset.Charset

import better.files.File
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.db.util.Db
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import modules._
import org.joda.time.DateTime
import plugins._

import scala.concurrent.Await
import scala.concurrent.duration._
import scalaxb.DispatchHttpClientsAsync

object Main extends App {

  implicit val charset: Charset = Charset.forName("UTF-8")

  Db.init()

  dynamicCodeRecompile()
  moduleConfigs()
  bgsGroups()
  addresses()
  contractPaymentTypes()
  contractParametersPrefs()
  contractParameterGroups()
  сontractParameterType7Values()
  entitySpecs()
  modulesAndServices()
  plugins()
  scheduledTasks()
  Inet.deviceTypes()
  Inet.deviceGroups()
  Inet.vlanResources()
  Inet.ipResources()
  Inet.trafficTypes()
  Inet.trafficTypeLinks()
  Inet.options()
  Inet.devices()
  Inet.servTypes()
  Inet.deviceReload()

  println("Finished. Press Ctrl+C")

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Автоматизация -> Управление динамическим кодом -> Скомпилировать всё
  //
  private def dynamicCodeRecompile(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class DynamicCodeServiceCake extends DynamicCodeServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("dynamic-code-service"))
    }
    val dynamicCodeServiceCake = new DynamicCodeServiceCake
    val dynamicCode = dynamicCodeServiceCake.service

    val responseFuture = dynamicCode.recompileAll()
    Await.result(responseFuture, 5.minutes)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Настройка -> Конфигурация
  // Модули -> ЭКЗЕМПЛЯР_МОДУЛЯ -> Конфигурация модуля
  //
  private def moduleConfigs(): Unit = {
    val modules = Seq(
      // Ядро
      "kernel",
      // Модули интернета
      "inet",
      // Модули абонентских плат и разовых услуг
      "npay",
      "rscm",
      "subscription",
      // Модули платежных систем
      "moneta",
      "qiwi",
      "mps",
      // Прочее
      "bill"
    )
    for (i <- modules.indices) {
      ModuleConfig.create(mid = Some(i), dt = DateTime.now(), title = "Default", active = 1, uid = Some(1),
        config = Some(File(s"bgbilling/${modules(i)}.conf").contentAsString)
      )
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Пользователи и права -> Группы
  //
  private def bgsGroups(): Unit = {
    BgsGroup.create(title = "Системные администраторы", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Техническая поддержка", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Строительный отдел", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Абонентский отдел", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Ресепшен", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Ночной колл-центр", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Адреса
  //
  private def addresses(): Unit = {
    val countryId = AddressCountry.create(title = "РФ").id
    val cityId = AddressCity.create(countryId = countryId, title = "г. Звенигород").id
    val streetId = AddressStreet.create(cityid = cityId, title = "ул. Мира", pIndex = "143180").id
    val houseId = AddressHouse.create(streetid = streetId, house = 1, frac = Some("Б"), amount = 128, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Типы платежей
  //
  private def contractPaymentTypes(): Unit = {
    ContractPaymentTypes.create(title = "Наличные", up = 0, `type` = 0, flag = 0)
    ContractPaymentTypes.create(title = "Банковская карта (офлайн)", up = 0, `type` = 0, flag = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - параметры
  //
  private def contractParametersPrefs(): Unit = {
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
  private def contractParameterGroups(): Unit = {
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
  private def сontractParameterType7Values(): Unit = {
    ContractParameterType7Values.create(pid = 12, title = "ООО")
    ContractParameterType7Values.create(pid = 12, title = "ЗАО")
    ContractParameterType7Values.create(pid = 12, title = "ПАО")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Атрибуты -> Атрибуты
  // Справочники -> Атрибуты -> Сущности
  //
  private def entitySpecs(): Unit = {
    EntitySpecAttr.create(title = "Адрес", `type` = 8, comment = "")
    EntitySpecAttr.create(title = "S/N", `type` = 1, comment = "")
    EntitySpec.create(title = "Коммутатор", entityspectypeid = 0, comment = "", hidden = 0, entitytitlemacros = "")
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 1, pos = 0)
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 2, pos = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  private def modulesAndServices(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class Cake extends ModuleServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("module-service"))
    }
    val cake = new Cake
    val service = cake.service

    Inet.moduleAndServices(service)
    Npay.moduleAndServices(service)
    Rscm.moduleAndServices(service)
    Subscription.moduleAndServices(service)
    Moneta.moduleAndServices(service)
    Qiwi.moduleAndServices(service)
    Mps.moduleAndServices(service)
    Bill.moduleAndServices(service)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Плагины -> Настройки плагинов
  //
  private def plugins(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class Cake extends PlugincfgServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("plugincfg-service"))
    }
    val cake = new Cake
    val service = cake.service

    Bonus.plugin(service)
    Dispatch.plugin(service)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  private def scheduledTasks() = {
    Kernel.scheduledTasks()
    Npay.scheduledTasks()
    Rscm.scheduledTasks()
    Bonus.scheduledTasks()
    Dispatch.scheduledTasks()
  }

}
