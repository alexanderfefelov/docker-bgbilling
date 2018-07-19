package modules

import com.github.alexanderfefelov.bgbilling.api.action.kernel._
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import org.joda.time.DateTime
import scalaxb._

import scala.concurrent.Await
import scala.concurrent.duration._

object Kernel {

  val now: DateTime = DateTime.now()

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

    class Cake extends DynamicCodeServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("dynamic-code-service"))
    }
    val service = new Cake().service

    val responseFuture = service.recompileAll()
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

    var cityId = AddressCity.create(countryId = countryId, title = "Звенигород г.").id

    var streetId = AddressStreet.create(cityid = cityId, title = "Абрикосовая ул.", pIndex = "143180").id
    AddressHouse.create(streetid = streetId, house = 1, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
    AddressHouse.create(streetid = streetId, house = 1, frac = Some("Б"), amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
    AddressHouse.create(streetid = streetId, house = 2, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id

    streetId = AddressStreet.create(cityid = cityId, title = "Виноградная ул.", pIndex = "143180").id
    AddressHouse.create(streetid = streetId, house = 4, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
    AddressHouse.create(streetid = streetId, house = 6, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id

    streetId = AddressStreet.create(cityid = cityId, title = "Тенистая ул.", pIndex = "143180").id
    AddressHouse.create(streetid = streetId, house = 1, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
    AddressHouse.create(streetid = streetId, house = 2, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
    AddressHouse.create(streetid = streetId, house = 3, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id

    cityId = AddressCity.create(countryId = countryId, title = "Одинцовский р-н, Ершово с.").id

    streetId = AddressStreet.create(cityid = cityId, title = "-", pIndex = "143055").id
    AddressHouse.create(streetid = streetId, house = 10, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
    AddressHouse.create(streetid = streetId, house = 12, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
    AddressHouse.create(streetid = streetId, house = 13, frac = None, amount = 0, comment = None, areaid = 0, quarterid = 0, boxIndex = None, dt = None, podDiapazon = "", pod = "").id
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Типы платежей
  //
  def contractPaymentTypes(): Unit = {
    /* 1 */ ContractPaymentTypes.create(title = "Наличные", up = 0, `type` = 0, flag = 0)
    /* 2 */ ContractPaymentTypes.create(title = "Банковская карта (офлайн)", up = 0, `type` = 0, flag = 0)
    /* 3 */ ContractPaymentTypes.create(title = "Банковский перевод", up = 0, `type` = 0, flag = 1)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Типы расходов
  //
  def contractChargeTypes(): Unit = {
    /* 1 */ ContractChargeTypes.create(title = "Подключение", up = 0, `type` = 0, flag = 0, payback = false)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - параметры
  //
  def contractParametersPrefs(): Unit = {
    // Общее
    //
    /*  1 */ ContractParametersPref.create(pt = 1, title = "Логин", sort = 1, script = "", flags = 1, lm = now)
    /*  2 */ ContractParametersPref.create(pt = 1, title = "Лицевой счёт", sort = 1, script = "", flags = 1, lm = now)
    /*  3 */ ContractParametersPref.create(pt = 2, title = "Адрес подключения", sort = 1, script = "", flags = 1, lm = now)
    /*  4 */ ContractParametersPref.create(pt = 9, title = "Телефон", sort = 1, script = "", flags = 1, lm = now)
    /*  5 */ ContractParametersPref.create(pt = 3, title = "Email", sort = 1, script = "", flags = 1, lm = now)

    // Физ. лица
    //
    /*  6 */ ContractParametersPref.create(pt = 1, title = "Фамилия", sort = 1, script = "", flags = 1, lm = now)
    /*  7 */ ContractParametersPref.create(pt = 1, title = "Имя", sort = 1, script = "", flags = 1, lm = now)
    /*  8 */ ContractParametersPref.create(pt = 1, title = "Отчество", sort = 1, script = "", flags = 1, lm = now)
    /*  9 */ ContractParametersPref.create(pt = 7, title = "Тип удостоверения личности", sort = 1, script = "", flags = 1, lm = now)
    /* 10 */ ContractParametersPref.create(pt = 1, title = "Данные удостоверения личности", sort = 1, script = "", flags = 1, lm = now)
    /* 11 */ ContractParametersPref.create(pt = 1, title = "Адрес регистрации", sort = 1, script = "", flags = 1, lm = now)
    /* 12 */ ContractParametersPref.create(pt = 6, title = "Дата рождения", sort = 1, script = "", flags = 1, lm = now)
    /* 13 */ ContractParametersPref.create(pt = 1, title = "Место рождения", sort = 1, script = "", flags = 1, lm = now)

    // Юр. лица
    //
    /* 14 */ ContractParametersPref.create(pt = 1, title = "Название", sort = 1, script = "", flags = 1, lm = now)
    /* 15 */ ContractParametersPref.create(pt = 1, title = "Адрес юридический", sort = 1, script = "", flags = 1, lm = now)
    /* 16 */ ContractParametersPref.create(pt = 7, title = "Организационно-правовая форма", sort = 1, script = "", flags = 1, lm = now)
    /* 17 */ ContractParametersPref.create(pt = 1, title = "ИНН", sort = 1, script = "", flags = 1, lm = now)
    /* 18 */ ContractParametersPref.create(pt = 1, title = "Адрес для выставления счетов", sort = 1, script = "", flags = 1, lm = now)
    /* 19 */ ContractParametersPref.create(pt = 1, title = "КПП", sort = 1, script = "", flags = 1, lm = now)
    /* 20 */ ContractParametersPref.create(pt = 1, title = "ОГРН", sort = 1, script = "", flags = 1, lm = now)
    /* 21 */ ContractParametersPref.create(pt = 1, title = "ОКАТО", sort = 1, script = "", flags = 1, lm = now)
    /* 22 */ ContractParametersPref.create(pt = 1, title = "ОКТМО", sort = 1, script = "", flags = 1, lm = now)
    /* 23 */ ContractParametersPref.create(pt = 1, title = "ОКВЭД", sort = 1, script = "", flags = 1, lm = now)
    /* 24 */ ContractParametersPref.create(pt = 1, title = "ОКПО", sort = 1, script = "", flags = 1, lm = now)
    /* 25 */ ContractParametersPref.create(pt = 1, title = "Банк: БИК", sort = 1, script = "", flags = 1, lm = now)
    /* 26 */ ContractParametersPref.create(pt = 1, title = "Банк: название", sort = 1, script = "", flags = 1, lm = now)
    /* 27 */ ContractParametersPref.create(pt = 1, title = "Банк: К/С", sort = 1, script = "", flags = 1, lm = now)
    /* 28 */ ContractParametersPref.create(pt = 1, title = "Банк: Р/С", sort = 1, script = "", flags = 1, lm = now)
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
    ContractParameterGroup.create(gid = 1, pid = 11)
    ContractParameterGroup.create(gid = 1, pid = 12)
    ContractParameterGroup.create(gid = 1, pid = 13)
    /* 2 */ ContractParameterGroupName.create("Юр. лицо")
    ContractParameterGroup.create(gid = 2, pid = 1)
    ContractParameterGroup.create(gid = 2, pid = 2)
    ContractParameterGroup.create(gid = 2, pid = 3)
    ContractParameterGroup.create(gid = 2, pid = 4)
    ContractParameterGroup.create(gid = 2, pid = 5)
    ContractParameterGroup.create(gid = 2, pid = 14)
    ContractParameterGroup.create(gid = 2, pid = 15)
    ContractParameterGroup.create(gid = 2, pid = 16)
    ContractParameterGroup.create(gid = 2, pid = 17)
    ContractParameterGroup.create(gid = 2, pid = 18)
    ContractParameterGroup.create(gid = 2, pid = 19)
    ContractParameterGroup.create(gid = 2, pid = 20)
    ContractParameterGroup.create(gid = 2, pid = 21)
    ContractParameterGroup.create(gid = 2, pid = 22)
    ContractParameterGroup.create(gid = 2, pid = 23)
    ContractParameterGroup.create(gid = 2, pid = 24)
    ContractParameterGroup.create(gid = 2, pid = 25)
    ContractParameterGroup.create(gid = 2, pid = 26)
    ContractParameterGroup.create(gid = 2, pid = 27)
    ContractParameterGroup.create(gid = 2, pid = 28)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - значения списков -> Значения списков
  //
  def сontractParameterType7Values(): Unit = {
    /* 1 */ ContractParameterType7Values.create(pid = 9, title = "Паспорт гражданина РФ")
    /* 2 */ ContractParameterType7Values.create(pid = 9, title = "Удостоверение личности военнослужащего РФ")
    /* 3 */ ContractParameterType7Values.create(pid = 9, title = "Военный билет")
    /* 4 */ ContractParameterType7Values.create(pid = 9, title = "Временное удостоверение личности гражданина РФ")

    /* 5 */ ContractParameterType7Values.create(pid = 16, title = "ИП")
    /* 6 */ ContractParameterType7Values.create(pid = 16, title = "ООО")
    /* 7 */ ContractParameterType7Values.create(pid = 16, title = "ЗАО")
    /* 8 */ ContractParameterType7Values.create(pid = 16, title = "ПАО")
    /* 9 */ ContractParameterType7Values.create(pid = 16, title = "ОАО")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Домены
  //
  def domains(): Unit = {
    /* 1 */ Domain.create(parentid = 0, title = "foo", comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit")
    /* 2 */ Domain.create(parentid = 0, title = "bar", comment = "Quisque vitae efficitur risus")
    /* 3 */ Domain.create(parentid = 2, title = "baz", comment = "Mauris eleifend non lorem quis sagittis")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Тарифные планы
  //
  def tariffs(): Unit = {
    case class TwoIds(var id1: Int, var id2: Int) { // https://stackoverflow.com/a/6196814/9152483
      def update(ab: (Int, Int)): Unit = {
        id1 = ab._1
        id2 = ab._2
      }
    }

    // Интернет-1
    //
    var tariffIdtreeId = TariffActions.addTariffPlan(used = 1)
    TariffActions.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Интернет-1", title_web = "Интернет-1", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)
    // Создаем тарифное поддерево модуля inet
    var moduleId = TariffActions.bgBillingModuleId("inet")
    var mtreeId = ModuleTariffTree.create(moduleId, tariffIdtreeId._2, 0, now.getMillis).id // ActionCreateMtree не возвращает идентификатор созданного объекта, поэтому обращаемся напрямую к БД
    var rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Добавляем типы трафика
    var trafficTypeId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "trafficType")
    TariffActions.modifTariffNode_update(id = trafficTypeId, data = "trafficTypeId&0,1,2")
    // Добавляем услугу
    var serviceSetId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "serviceSet")
    TariffActions.modifTariffNode_update(id = serviceSetId, data = "serviceId&1")
    // Добавляем стоимости трафика
    var costId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "type&3%col&1%cost&0.0")
    costId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "type&6%col&1%cost&0.0")
    // Добавляем опции
    var optionId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffActions.modifTariffNode_update(id = optionId, data = "inetOptionId&2")
    optionId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffActions.modifTariffNode_update(id = optionId, data = "inetOptionId&6")
    // Создаем тарифное поддерево модуля npay
    moduleId = TariffActions.bgBillingModuleId("npay")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdtreeId._2, 0, now.getMillis).id
    rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем помесячную абонентскую плату
    var monthModeId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "month_mode")
    TariffActions.modifTariffNode_update(id = monthModeId, data = "mode&month%sid&3")
    // Добавляем стоимость
    var monthCostId = TariffActions.modifTariffNode_create(parent = monthModeId, mtree_id = mtreeId, typ = "month_cost")
    TariffActions.modifTariffNode_update(id = monthCostId, data = "cost&500.0%type&1")

    // Интернет-2
    //
    tariffIdtreeId = TariffActions.addTariffPlan(used = 1)
    TariffActions.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Интернет-2", title_web = "Интернет-2", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)
    // Создаем тарифное поддерево модуля inet
    moduleId = TariffActions.bgBillingModuleId("inet")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdtreeId._2, 0, now.getMillis).id
    rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Добавляем типы трафика
    trafficTypeId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "trafficType")
    TariffActions.modifTariffNode_update(id = trafficTypeId, data = "trafficTypeId&0,1,2")
    // Добавляем услугу
    serviceSetId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "serviceSet")
    TariffActions.modifTariffNode_update(id = serviceSetId, data = "serviceId&1")
    // Добавляем стоимости трафика
    costId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "type&3%col&1%cost&0.0")
    costId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "type&6%col&1%cost&0.0")
    // Добавляем опции
    optionId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffActions.modifTariffNode_update(id = optionId, data = "inetOptionId&3")
    optionId = TariffActions.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffActions.modifTariffNode_update(id = optionId, data = "inetOptionId&5")
    // Создаем тарифное поддерево модуля npay
    moduleId = TariffActions.bgBillingModuleId("npay")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdtreeId._2, 0, now.getMillis).id
    rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем помесячную абонентскую плату
    monthModeId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "month_mode")
    TariffActions.modifTariffNode_update(id = monthModeId, data = "mode&month%sid&3")
    // Добавляем стоимость
    monthCostId = TariffActions.modifTariffNode_create(parent = monthModeId, mtree_id = mtreeId, typ = "month_cost")
    TariffActions.modifTariffNode_update(id = monthCostId, data = "cost&1000.0%type&1")

    // Канал L2
    //
    tariffIdtreeId = TariffActions.addTariffPlan(used = 1)
    TariffActions.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Канал L2", title_web = "Канал L2", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)

    // Разовые услуги
    //
    tariffIdtreeId = TariffActions.addTariffPlan(used = 1)
    TariffActions.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Разовые услуги", title_web = "Разовые услуги", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)
    // Создаем тарифное поддерево модуля rscm
    moduleId = TariffActions.bgBillingModuleId("rscm")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdtreeId._2, 0, now.getMillis).id
    rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем услугу
    var serviceId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffActions.modifTariffNode_update(id = serviceId, data = "4")
    // Добавляем период
    var periodId = TariffActions.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "period")
    TariffActions.modifTariffNode_update(id = periodId, data = "date1&01.07.2018%date2&30.06.2019")
    // Добавляем стоимость
    costId = TariffActions.modifTariffNode_create(parent = periodId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "col&1%cost&100.0")
    // Создаем услугу
    serviceId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffActions.modifTariffNode_update(id = serviceId, data = "5")
    // Добавляем период
    periodId = TariffActions.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "period")
    TariffActions.modifTariffNode_update(id = periodId, data = "date1&01.07.2018%date2&30.06.2019")
    // Добавляем стоимость
    costId = TariffActions.modifTariffNode_create(parent = periodId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "col&1%cost&0.0")
    // Создаем услугу
    serviceId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffActions.modifTariffNode_update(id = serviceId, data = "6")
    // Добавляем период
    periodId = TariffActions.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "period")
    TariffActions.modifTariffNode_update(id = periodId, data = "date1&01.07.2018%date2&30.06.2019")
    // Добавляем стоимость
    costId = TariffActions.modifTariffNode_create(parent = periodId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "col&1%cost&300.0")

    // Товары
    //
    tariffIdtreeId = TariffActions.addTariffPlan(used = 1)
    TariffActions.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Товары", title_web = "Товары", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)
    // Создаем тарифное поддерево модуля rscm
    moduleId = TariffActions.bgBillingModuleId("rscm")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdtreeId._2, 0, now.getMillis).id
    rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем товар
    serviceId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffActions.modifTariffNode_update(id = serviceId, data = "7")
    // Добавляем период
    periodId = TariffActions.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "period")
    TariffActions.modifTariffNode_update(id = periodId, data = "date1&01.07.2018%date2&30.06.2019")
    // Добавляем стоимость
    costId = TariffActions.modifTariffNode_create(parent = periodId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "col&1%cost&3000.0")
    // Создаем товар
    serviceId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffActions.modifTariffNode_update(id = serviceId, data = "8")
    // Добавляем период
    periodId = TariffActions.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "period")
    TariffActions.modifTariffNode_update(id = periodId, data = "date1&01.07.2018%date2&30.06.2019")
    // Добавляем стоимость
    costId = TariffActions.modifTariffNode_create(parent = periodId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "col&1%cost&3100.0")
    // Создаем товар
    serviceId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffActions.modifTariffNode_update(id = serviceId, data = "9")
    // Добавляем период
    periodId = TariffActions.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "period")
    TariffActions.modifTariffNode_update(id = periodId, data = "date1&01.07.2018%date2&30.06.2019")
    // Добавляем стоимость
    costId = TariffActions.modifTariffNode_create(parent = periodId, mtree_id = mtreeId, typ = "cost")
    TariffActions.modifTariffNode_update(id = costId, data = "col&1%cost&3200.0")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Договор -> Новый договор
  //
  def contracts(): Unit = {
    /* 23456 */ var cid = ContractActions.newContract(date = now.minusMonths(3), pattern_id = 1)
    ContractActions.updateParameterType1(cid = cid, pid = 6, value = "Швейк")
    ContractActions.updateParameterType1(cid = cid, pid = 7, value = "Йозеф")
    ContractActions.updateContractTariffPlan(id = 0, cid = cid, tpid = 1, date1 = DateTime.parse("01.01.2018", dateFormatter))
    ContractActions.updateAddressInfo(cid = cid, pid = 3, hid = 1, pod = 4, floor = 5, flat = "6Б")

    /* 23457 */ cid = ContractActions.newContract(date = now, pattern_id = 4)
    ContractActions.updateParameterType1(cid = cid, pid = 14, value = "Вектор")
    ContractActions.updateListParameter(cid = cid, pid = 16, value = 6)
    ContractActions.updateContractTariffPlan(id = 0, cid = cid, tpid = 2, date1 = DateTime.parse("01.04.2018", dateFormatter))
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Договор -> Открыть договор -> ДОГОВОР -> Примечания
  //
  def contractComments(): Unit = {
    ContractComment.create(cid = 23456, uid = 1, subject = "Комплексный фторид церия как электронное облако", comment =
      """
        |Руководствуясь периодическим законом, бертолетова соль испаряет белок так, как это могло влиять на реакцию Дильса-Альдера. Самосогласованная модель предсказывает, что при определенных условиях пламя сублимирует электролиз. В самом общем случае выпаривание сублимирует краситель.
        |
        |Неустойчивость, как известно, быстро разивается, если амальгама возгоняет энергетический подуровень без тонкослойной хроматограммы. В ряде недавних экспериментов белок избирательно активирует газообразный диэтиловый эфир, при этом наноразмерные частички золота создают мицеллу. Бертолетова соль стационарно испаряет гидрогенит. Бюретка, как и везде в пределах наблюдаемой вселенной, ядовита. В условиях электромагнитных помех, неизбежных при полевых измерениях, не всегда можно определить, когда именно пламя активирует этиловый валентный электрон.
        |
        |Цепочка ДНК синтезирует сернистый газ. Подкисление эффективно адсорбирует рацемический 238 изотоп урана. Биуретовая реакция гидролизует ионный кетон. Электролиз методически нейтрализует пептидный белый пушистый осадок, поэтому перед употреблением взбалтывают. Голубой гель неустойчив.
        |
      """.stripMargin, dt = now.minusMonths(1), visibled = false)
    ContractComment.create(cid = 23456, uid = 1, subject = "Плазменный газ: основные моменты", comment =
      """
        |Излучение противоречиво испускает векторный фронт. Поток отклоняет гидродинамический удар, как и предсказывает общая теория поля. Фронт устойчив в магнитном поле.
        |
        |Резонатор, в согласии с традиционными представлениями, отражает расширяющийся фонон. Любое возмущение затухает, если суспензия едва ли квантуема. Колебание отрицательно заряжено. Сингулярность заряжает нестационарный взрыв. Луч, несмотря на некоторую вероятность коллапса, бифокально облучает электрон так, как это могло бы происходить в полупроводнике с широкой запрещенной зоной.
        |
        |Еще в ранних работах Л.Д.Ландау показано, что атом гомогенно расщепляет газ. Фронт квантово разрешен. Ударная волна спонтанно ускоряет гидродинамический удар. Магнит когерентно вращает расширяющийся бозе-конденсат. В литературе неоднократно описано, как расслоение мономолекулярно сжимает ультрафиолетовый эксимер в полном соответствии с законом сохранения энергии.
        |
      """.stripMargin, dt = now.minusWeeks(1), visibled = false)
    ContractComment.create(cid = 23457, uid = 1, subject = "Стремящийся интеграл Гамильтона: гипотеза и теории", comment =
      """
        |График функции многих переменных, конечно, восстанавливает комплексный интеграл по поверхности. Тем не менее, лист Мёбиуса существенно обуславливает детерминант. Подынтегральное выражение специфицирует ротор векторного поля. Не факт, что начало координат трансформирует метод последовательных приближений. Геодезическая линия отражает эмпирический определитель системы линейных уравнений.
        |
        |Интеграл по бесконечной области, общеизвестно, отображает минимум. Интеграл Дирихле отражает ряд Тейлора. Арифметическая прогрессия вырождена. Интерполяция по-прежнему востребована. Надо сказать, что дифференциальное уравнение небезынтересно масштабирует коллинеарный предел последовательности. Интегрирование по частям в принципе стабилизирует расходящийся ряд.
        |
        |Очевидно проверяется, что график функции переворачивает тригонометрический двойной интеграл. Метод последовательных приближений притягивает ряд Тейлора. Ортогональный определитель охватывает изоморфный неопределенный интеграл.
        |
      """.stripMargin, dt = now.minusDays(1), visibled = false)
    ContractComment.create(cid = 23457, uid = 1, subject = "Почему неравномерен параллакс?", comment =
      """
        |Прямое восхождение представляет собой далекий перигелий. Декретное время перечеркивает центральный эффективный диаметp. Юлианская дата притягивает космический сарос, день этот пришелся на двадцать шестое число месяца карнея, который у афинян называется метагитнионом.
        |
        |Метеорит представляет собой близкий поперечник, но кольца видны только при 40–50. Восход , это удалось установить по характеру спектра, меняет непреложный математический горизонт. У планет-гигантов нет твёрдой поверхности, таким образом прямое восхождение меняет далекий тропический год. Атомное время, после осторожного анализа, отражает ионный хвост. Космический мусор на следующий год, когда было лунное затмение и сгорел древний храм Афины в Афинах (при эфоре Питии и афинском архонте Каллии), гасит космический метеорный дождь.
        |
        |Исполинская звездная спираль с поперечником в 50 кпк, и это следует подчеркнуть, выслеживает эллиптический Юпитер, но кольца видны только при 40–50. Полнолуние меняет pадиотелескоп Максвелла. Бесспорно, гелиоцентрическое расстояние притягивает лимб.
        |
      """.stripMargin, dt = now, visibled = false)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Договор -> Открыть договор -> ДОГОВОР -> Приход
  //
  def payments(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._

    class Cake extends PaymentServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("payment-service"))
    }
    val service = new Cake().service

    val dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZZ"

    var payment = Payment(comment = Some("Какой-то комментарий"), attributes = Map(
      "id" ->         dr("id", -1),
      "contractId" -> dr("contractId", 23456),
      "date" ->       dr("date", now.minusMonths(2).toString(dateTimeFormat)),
      "sum" ->        dr("sum", 100.0),
      "summa" ->      dr("summa", 100.0),
      "typeId" ->     dr("typeId", 1),
      "userId" ->     dr("userId", 0)
    ))
    var responseFuture = service.paymentUpdate(Some(payment), None)
    Await.result(responseFuture, 15.seconds)

    payment = Payment(comment = None, attributes = Map(
      "id" ->         dr("id", -1),
      "contractId" -> dr("contractId", 23456),
      "date" ->       dr("date", now.minusMonths(3).toString(dateTimeFormat)),
      "sum" ->        dr("sum", 70.0),
      "summa" ->      dr("summa", 70.0),
      "typeId" ->     dr("typeId", 2),
      "userId" ->     dr("userId", 0)
    ))
    responseFuture = service.paymentUpdate(Some(payment), None)
    Await.result(responseFuture, 15.seconds)

    payment = Payment(comment = None, attributes = Map(
      "id" ->         dr("id", -1),
      "contractId" -> dr("contractId", 23456),
      "date" ->       dr("date", now.toString(dateTimeFormat)),
      "sum" ->        dr("sum", 10.0),
      "summa" ->      dr("summa", 10.0),
      "typeId" ->     dr("typeId", 1),
      "userId" ->     dr("userId", 0)
    ))
    responseFuture = service.paymentUpdate(Some(payment), None)
    Await.result(responseFuture, 15.seconds)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - шаблоны комментариев
  //
  def contractCommentPatterns(): Unit = {
    /* 1 */ ContractCommentPatterns.create(title = "ФИО + Л/С", pat = "${param_6} ${param_7} ${param_8}, Л/С ${param_2}")
    /* 2 */ ContractCommentPatterns.create(title = "Название + ОПФ + Л/С", pat = "${param_14} ${param_16}, Л/С ${param_2}")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Договор -> Шаблоны
  //
  def contractPatterns(): Unit = {
    val data =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<data webMenuId="-1">
        |    <modules>
        |        <inet mid="1">
        |            <addInetServ enable="0" inetServTypeId="1" sessionCountLimit="0" status="0"/>
        |        </inet>
        |        <npay mid="2">
        |            <addServices/>
        |        </npay>
        |        <rscm mid="3">
        |            <currentServices/>
        |        </rscm>
        |    </modules>
        |    <plugins>
        |        <bonus include="false">
        |            <addBonusProgram/>
        |        </bonus>
        |    </plugins>
        |    <general dtl="0" status="5"/>
        |</data>
        |""".stripMargin
    /* 1 */ ContractPattern.create(title = "Ф/Л, аванс",
      closesumma = 0.0f, // Лимит
      tpid = "4,5", // Тарифы
      groups = 0,
      mode = 1, // Дебет
      pgid = 1, // Группа параметров: Физ. лицо
      pfid = 0,
      fc = 0, // Физ. лицо
      dtl = 0, tgid = "", scrid = "", namePattern = "А-${Y2}-${N4}", data = Some(data.getBytes),
      patid = 1, // Шаблон комментария
      status = 0, domainid = 1
    )
    /* 2 */ ContractPattern.create(title = "Ф/Л, кредит",
      closesumma = 0.0f, // Лимит
      tpid = "4,5", // Тарифы
      groups = 0,
      mode = 0, // Кредит
      pgid = 1, // Группа параметров: Физ. лицо
      pfid = 0,
      fc = 0, // Физ. лицо
      dtl = 0, tgid = "", scrid = "", namePattern = "Б-${Y2}-${N4}", data = Some(data.getBytes),
      patid = 1, // Шаблон комментария
      status = 0, domainid = 1
    )
    /* 3 */ ContractPattern.create(title = "Ю/Л, аванс, 3 дня",
      closesumma = 0.0f, // Лимит
      tpid = "", groups = 0,
      mode = 1, // Дебет
      pgid = 2, // Группа параметров: Юр. лицо
      pfid = 0,
      fc = 1, // Юр. лицо
      dtl = 3, // Время жизни
      tgid = "", scrid = "", namePattern = "В-${Y2}-${N4}", data = Some(data.getBytes),
      patid = 2, // Шаблон комментария
      status = 0, domainid = 3
    )
    /* 4 */ ContractPattern.create(title = "Ю/Л, кредит, лимит -20000 руб.",
      closesumma = -20000.0f, // Лимит
      tpid = "", groups = 0,
      mode = 0, // Кредит
      pgid = 2, // Группа параметров: Юр. лицо
      pfid = 0,
      fc = 1, // Юр. лицо
      dtl = 0, tgid = "", scrid = "", namePattern = "Г-${Y2}-${N4}", data = Some(data.getBytes),
      patid = 2, // Шаблон комментария
      status = 0, domainid = 3
    )
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
