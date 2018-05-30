package modules

import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._


object Kernel {

  val now = DateTime.now()

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  def scheduledTasks() = {
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 16, min = 1048576, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "ru.bitel.bgbilling.kernel.task.server.Validator", moduleId = "0", comment = "Проверка базы данных биллинга на корректность", params = "email=admin@inter.net")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Автоматизация -> Управление динамическим кодом -> Скомпилировать всё
  //
  def dynamicCodeRecompile(): Unit = {
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
  // Сервис -> Администрирование -> Пользователи и права -> Группы
  //
  def bgsGroups(): Unit = {
    BgsGroup.create(title = "Системные администраторы", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Техническая поддержка", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Строительный отдел", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Абонентский отдел", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Ресепшен", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Бухгалтерия", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
    BgsGroup.create(title = "Ночной колл-центр", comment = "", cgr = 0, pids = None, opids = None, cgrMode = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Адреса
  //
  def addresses(): Unit = {
    val countryId = AddressCountry.create(title = "РФ").id
    val cityId = AddressCity.create(countryId = countryId, title = "г. Звенигород").id
    val streetId = AddressStreet.create(cityid = cityId, title = "ул. Мира", pIndex = "143180").id
    val houseId = AddressHouse.create(streetid = streetId, house = 1, frac = Some("Б"), amount = 128, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Типы платежей
  //
  def contractPaymentTypes(): Unit = {
    ContractPaymentTypes.create(title = "Наличные", up = 0, `type` = 0, flag = 0)
    ContractPaymentTypes.create(title = "Банковская карта (офлайн)", up = 0, `type` = 0, flag = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - параметры
  //
  def contractParametersPrefs(): Unit = {
    // Общее
    //
    /*  1 */ ContractParametersPref.create(pt = 7, title = "Домен", sort = 1, script = "", flags = 1, lm = now)
    /*  2 */ ContractParametersPref.create(pt = 1, title = "Логин", sort = 1, script = "", flags = 1, lm = now)
    /*  3 */ ContractParametersPref.create(pt = 1, title = "Лицевой счёт", sort = 1, script = "", flags = 1, lm = now)
    /*  4 */ ContractParametersPref.create(pt = 2, title = "Адрес подключения", sort = 1, script = "", flags = 1, lm = now)
    /*  5 */ ContractParametersPref.create(pt = 9, title = "Телефон(ы) для связи", sort = 1, script = "", flags = 1, lm = now)
    /*  6 */ ContractParametersPref.create(pt = 3, title = "Email", sort = 1, script = "", flags = 1, lm = now)

    // Физ. лица
    //
    /*  7 */ ContractParametersPref.create(pt = 1, title = "Адрес регистрации", sort = 1, script = "", flags = 1, lm = now)
    /*  8 */ ContractParametersPref.create(pt = 1, title = "Данные паспорта", sort = 1, script = "", flags = 1, lm = now)
    /*  9 */ ContractParametersPref.create(pt = 6, title = "Дата рождения", sort = 1, script = "", flags = 1, lm = now)
    /* 10 */ ContractParametersPref.create(pt = 1, title = "Место рождения", sort = 1, script = "", flags = 1, lm = now)

    // Юр. лица
    //
    /* 11 */ ContractParametersPref.create(pt = 1, title = "Адрес юридический", sort = 1, script = "", flags = 1, lm = now)
    /* 12 */ ContractParametersPref.create(pt = 1, title = "ИНН", sort = 1, script = "", flags = 1, lm = now)
    /* 13 */ ContractParametersPref.create(pt = 7, title = "Организационно-правовая форма", sort = 1, script = "", flags = 1, lm = now)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - группы параметров
  //
  def contractParameterGroups(): Unit = {
    /* 1 */ ContractParameterGroupName.create("Физ. лицо")
    ContractParameterGroup.create(gid = 1, pid = 1)
    ContractParameterGroup.create(gid = 1, pid = 2)
    ContractParameterGroup.create(gid = 1, pid = 3)
    ContractParameterGroup.create(gid = 1, pid = 4)
    ContractParameterGroup.create(gid = 1, pid = 5)
    ContractParameterGroup.create(gid = 1, pid = 6)
    ContractParameterGroup.create(gid = 1, pid = 7)
    ContractParameterGroup.create(gid = 1, pid = 8)
    ContractParameterGroup.create(gid = 1, pid = 9)
    ContractParameterGroup.create(gid = 1, pid = 10)
    /* 2 */ ContractParameterGroupName.create("Юр. лицо")
    ContractParameterGroup.create(gid = 2, pid = 1)
    ContractParameterGroup.create(gid = 1, pid = 2)
    ContractParameterGroup.create(gid = 1, pid = 3)
    ContractParameterGroup.create(gid = 1, pid = 4)
    ContractParameterGroup.create(gid = 1, pid = 5)
    ContractParameterGroup.create(gid = 2, pid = 10)
    ContractParameterGroup.create(gid = 2, pid = 11)
    ContractParameterGroup.create(gid = 2, pid = 12)
    ContractParameterGroup.create(gid = 2, pid = 13)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - значения списков -> Значения списков
  //
  def сontractParameterType7Values(): Unit = {
    ContractParameterType7Values.create(pid = 1, title = "zvenigorod-center")
    ContractParameterType7Values.create(pid = 1, title = "praha-nove-mesto")

    ContractParameterType7Values.create(pid = 13, title = "ООО")
    ContractParameterType7Values.create(pid = 13, title = "ЗАО")
    ContractParameterType7Values.create(pid = 13, title = "ПАО")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Атрибуты -> Атрибуты
  // Справочники -> Атрибуты -> Сущности
  //
  def entitySpecs(): Unit = {
    EntitySpecAttr.create(title = "Адрес", `type` = 8, comment = "")
    EntitySpecAttr.create(title = "S/N", `type` = 1, comment = "")
    EntitySpec.create(title = "Коммутатор", entityspectypeid = 0, comment = "", hidden = 0, entitytitlemacros = "")
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 1, pos = 0)
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 2, pos = 0)
  }

}