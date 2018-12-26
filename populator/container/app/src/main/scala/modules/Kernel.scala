package modules

import com.github.alexanderfefelov.bgbilling.api.action.kernel._
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import loaders._
import org.joda.time.DateTime
import scalaxb._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Kernel {

  val now: DateTime = DateTime.now()

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  def scheduledTasks() = {
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 0, min = 37191016277640225L, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "bitel.billing.server.script.TimerEventGenerate", moduleId = "0", comment = "Таймер 5 мин.", params = "flag=300\n")
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 16, min = 1048576, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "ru.bitel.bgbilling.kernel.task.server.Validator", moduleId = "0", comment = "Проверка базы данных биллинга на корректность", params = "email=admin@inter.net\n")
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 1, min = 1, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "bitel.billing.server.contract.LimitRestorer", moduleId = "0", comment = "Возвращение временно изменённых лимитов к исходному значению", params = "")
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 1, min = 1, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "bitel.billing.server.contract.ContractStatusSetter", moduleId = "0", comment = "Изменение статусов договоров в соответствии с заданиями", params = "")
    val params =
      """
        |tids=
        |type=1
        |autocommit=false
        |""".stripMargin
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 0, min = 0, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "bitel.billing.server.script.global.bean.GlobalScriptTimer", moduleId = "0", comment = "", params = params)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Автоматизация -> Управление динамическим кодом -> Скомпилировать всё
  //
  def dynamicCodeRecompile(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._

    class DynamicCodeCake extends DynamicCodeServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("dynamic-code-service"))
    }
    val dynamicCodeService = new DynamicCodeCake().service

    val responseFuture = dynamicCodeService.recompileAll()
    Await.result(responseFuture, 5.minutes)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Автоматизация -> Функции скриптов поведения
  // Сервис -> Автоматизация -> Функции глобальных событий
  //
  def eventHandlers(): Unit = {
    ScriptEventType.findAll().map { t =>
      EventScriptLink.create(title = t.title, className = "com.github.alexanderfefelov.bgbilling.dyn.kernel.event.murmuring.MurmuringEventHandler", eventKey = s"${t.mid}_${t.eventId}", scriptId = if (t.eventMode == 0) 0 else -1)
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Автоматизация -> Глобальные скрипты поведения
  //
  def globalScripts(): Unit = {
    GlobalScriptLink.create(title = "Murmur", className = "com.github.alexanderfefelov.bgbilling.dyn.kernel.global.MurmuringGlobalScript")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Утилиты -> SQL редактор
  //
  def sqlTemplates(): Unit = {
    var name = "Валидация: Найти договоры, не состоящие ни в одной группе договоров"
    var sql =
      s"""|-- $name
        |select
        |  c.id as cid,
        |  c.title,
        |  c.date1,
        |  c.date2,
        |  c.comment
        |from
        |  contract c
        |where
        |  c.gr = 0
      """.stripMargin
    SqlTemplate.create(userId = -1, title = name, text = sql)
    name = "Валидация: Найти договоры, не состоящие в группе договоров \"Все\""
    sql =
      s"""-- $name
        |select
        |  c.id as cid,
        |  c.title,
        |  c.date1,
        |  c.date2,
        |  c.comment
        |from
        |  contract c
        |where
        |  c.gr & 1 = 0
      """.stripMargin
    SqlTemplate.create(userId = -1, title = name, text = sql)
    name = "Валидация: Найти договоры без услуг модуля npay"
    sql =
      s"""-- $name
        |select
        |  c.id as cid,
        |  c.title,
        |  c.date1,
        |  c.date2,
        |  c.comment
        |from contract c
        |  left join npay_service_object_2 nso on nso.cid = c.id
        |  left join service s on s.id = nso.sid
        |  left join module m on m.id = s.mid and m.name = 'npay'
        |where
        |  nso.cid is null
      """.stripMargin
    SqlTemplate.create(userId = -1, title = name, text = sql)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Пользователи и права -> Пользователи
  //
  def users(): Unit = {
    Users.load()
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
    Addresses.load()
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Типы платежей
  //
  def contractPaymentTypes(): Unit = {
    PaymentTypes.load()
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Типы расходов
  // Справочники -> Другие -> Типы возвратов
  //
  def contractChargeTypes(): Unit = {
    ChargeTypes.load()
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - группы
  //
  def contractGroups(): Unit = {
    ContractGroup.create(id = 0, title = "Все", enable = 1, editable = 1, comment = "")
    ContractGroup.create(id = 1, title = "Ф/Л", enable = 1, editable = 1, comment = "")
    ContractGroup.create(id = 2, title = "Ю/Л", enable = 1, editable = 1, comment = "")
    ContractGroup.create(id = 3, title = "Специальный", enable = 1, editable = 1, comment = "")
    ContractGroup.create(id = 4, title = "Служебный", enable = 1, editable = 1, comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - параметры
  //
  def contractParametersPrefs(): Unit = {
    // flags:
    //   1 - история
    //   2 - правка в ЛК
    //   4 - чтение в ЛК
    //   8 - всегда видимый

    // Общее
    //
    /*  1 */ ContractParametersPref.create(pt = 1, title = "Логин", sort = 1, script = "",  flags = 1, lm = now)
    /*  2 */ ContractParametersPref.create(pt = 1, title = "Лицевой счёт", sort = 1, script = "",  flags = 1, lm = now)
    /*  3 */ ContractParametersPref.create(pt = 2, title = "Адрес подключения", sort = 1, script = "",  flags = 1, lm = now)
    /*  4 */ ContractParametersPref.create(pt = 9, title = "Телефон", sort = 1, script = "",  flags = 1, lm = now)
    /*  5 */ ContractParametersPref.create(pt = 3, title = "Email", sort = 1, script = "",  flags = 1, lm = now)

    // Физ. лица
    //
    /*  6 */ ContractParametersPref.create(pt = 1, title = "Фамилия", sort = 1, script = "",  flags = 1, lm = now)
    /*  7 */ ContractParametersPref.create(pt = 1, title = "Имя", sort = 1, script = "",  flags = 1, lm = now)
    /*  8 */ ContractParametersPref.create(pt = 1, title = "Отчество", sort = 1, script = "",  flags = 1, lm = now)
    /*  9 */ ContractParametersPref.create(pt = 7, title = "Удостоверение личности: тип", sort = 1, script = "",  flags = 1, lm = now)
    /* 10 */ ContractParametersPref.create(pt = 1, title = "Удостоверение личности: серия", sort = 1, script = "",  flags = 1, lm = now)
    /* 11 */ ContractParametersPref.create(pt = 1, title = "Удостоверение личности: номер", sort = 1, script = "",  flags = 1, lm = now)
    /* 12 */ ContractParametersPref.create(pt = 6, title = "Удостоверение личности: дата", sort = 1, script = "",  flags = 1, lm = now)
    /* 13 */ ContractParametersPref.create(pt = 1, title = "Удостоверение личности: код подразделения", sort = 1, script = "",  flags = 1, lm = now)
    /* 14 */ ContractParametersPref.create(pt = 1, title = "Удостоверение личности: название подразделения", sort = 1, script = "",  flags = 1, lm = now)
    /* 15 */ ContractParametersPref.create(pt = 1, title = "Адрес регистрации", sort = 1, script = "",  flags = 1, lm = now)
    /* 16 */ ContractParametersPref.create(pt = 6, title = "Дата рождения", sort = 1, script = "",  flags = 1, lm = now)
    /* 17 */ ContractParametersPref.create(pt = 1, title = "Место рождения", sort = 1, script = "",  flags = 1, lm = now)

    // Юр. лица
    //
    /* 18 */ ContractParametersPref.create(pt = 1, title = "Название", sort = 1, script = "",  flags = 1, lm = now)
    /* 19 */ ContractParametersPref.create(pt = 1, title = "Адрес юридический", sort = 1, script = "",  flags = 1, lm = now)
    /* 20 */ ContractParametersPref.create(pt = 5, title = "Бюджет", sort = 1, script = "",  flags = 1, lm = now)
    /* 21 */ ContractParametersPref.create(pt = 1, title = "ИНН", sort = 1, script = "",  flags = 1, lm = now)
    /* 22 */ ContractParametersPref.create(pt = 1, title = "Адрес для выставления счетов", sort = 1, script = "",  flags = 1, lm = now)
    /* 23 */ ContractParametersPref.create(pt = 1, title = "КПП", sort = 1, script = "",  flags = 1, lm = now)
    /* 24 */ ContractParametersPref.create(pt = 1, title = "ОГРН", sort = 1, script = "",  flags = 1, lm = now)
    /* 25 */ ContractParametersPref.create(pt = 1, title = "ОКАТО", sort = 1, script = "",  flags = 1, lm = now)
    /* 26 */ ContractParametersPref.create(pt = 1, title = "ОКТМО", sort = 1, script = "",  flags = 1, lm = now)
    /* 27 */ ContractParametersPref.create(pt = 1, title = "ОКВЭД", sort = 1, script = "",  flags = 1, lm = now)
    /* 28 */ ContractParametersPref.create(pt = 1, title = "ОКПО", sort = 1, script = "",  flags = 1, lm = now)
    /* 29 */ ContractParametersPref.create(pt = 1, title = "Банк: БИК", sort = 1, script = "",  flags = 1, lm = now)
    /* 30 */ ContractParametersPref.create(pt = 1, title = "Банк: название", sort = 1, script = "",  flags = 1, lm = now)
    /* 31 */ ContractParametersPref.create(pt = 1, title = "Банк: К/С", sort = 1, script = "",  flags = 1, lm = now)
    /* 32 */ ContractParametersPref.create(pt = 1, title = "Банк: Р/С", sort = 1, script = "",  flags = 1, lm = now)
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
    ContractParameterGroup.create(gid = 1, pid = 14)
    ContractParameterGroup.create(gid = 1, pid = 15)
    ContractParameterGroup.create(gid = 1, pid = 16)
    ContractParameterGroup.create(gid = 1, pid = 17)
    /* 2 */ ContractParameterGroupName.create("Юр. лицо")
    ContractParameterGroup.create(gid = 2, pid = 1)
    ContractParameterGroup.create(gid = 2, pid = 2)
    ContractParameterGroup.create(gid = 2, pid = 3)
    ContractParameterGroup.create(gid = 2, pid = 4)
    ContractParameterGroup.create(gid = 2, pid = 5)
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
    ContractParameterGroup.create(gid = 2, pid = 29)
    ContractParameterGroup.create(gid = 2, pid = 30)
    ContractParameterGroup.create(gid = 2, pid = 31)
    ContractParameterGroup.create(gid = 2, pid = 32)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - значения списков -> Значения списков
  //
  def contractParameterType7Values(): Unit = {
    /* 1 */ ContractParameterType7Values.create(pid = 9, title = "Паспорт гражданина РФ")
    /* 2 */ ContractParameterType7Values.create(pid = 9, title = "Удостоверение личности военнослужащего РФ")
    /* 3 */ ContractParameterType7Values.create(pid = 9, title = "Военный билет")
    /* 4 */ ContractParameterType7Values.create(pid = 9, title = "Временное удостоверение личности гражданина РФ")
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
    var tariffIdTreeId = TariffActions.addTariffPlan(used = true)
    TariffActions.updateTariffPlan(tpid = tariffIdTreeId._1, face = 0, title = "Интернет-1", title_web = "Интернет-1", use_title_in_web = false, values = "", config = "", mask = "", tpused = true)
    // Создаем тарифное поддерево модуля inet
    var moduleId = TariffActions.bgBillingModuleId("inet")
    var mtreeId = ModuleTariffTree.create(moduleId, tariffIdTreeId._2, 0, now.getMillis).id // ActionCreateMtree не возвращает идентификатор созданного объекта, поэтому обращаемся напрямую к БД
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
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdTreeId._2, 0, now.getMillis).id
    rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем помесячную абонентскую плату
    var monthModeId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "month_mode")
    TariffActions.modifTariffNode_update(id = monthModeId, data = "mode&month%sid&3")
    // Добавляем стоимость
    var monthCostId = TariffActions.modifTariffNode_create(parent = monthModeId, mtree_id = mtreeId, typ = "month_cost")
    TariffActions.modifTariffNode_update(id = monthCostId, data = "cost&500.0%type&1")

    // Интернет-2
    //
    tariffIdTreeId = TariffActions.addTariffPlan(used = true)
    TariffActions.updateTariffPlan(tpid = tariffIdTreeId._1, face = 0, title = "Интернет-2", title_web = "Интернет-2", use_title_in_web = false, values = "", config = "", mask = "", tpused = true)
    // Создаем тарифное поддерево модуля inet
    moduleId = TariffActions.bgBillingModuleId("inet")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdTreeId._2, 0, now.getMillis).id
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
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdTreeId._2, 0, now.getMillis).id
    rootId = TariffActions.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем помесячную абонентскую плату
    monthModeId = TariffActions.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "month_mode")
    TariffActions.modifTariffNode_update(id = monthModeId, data = "mode&month%sid&3")
    // Добавляем стоимость
    monthCostId = TariffActions.modifTariffNode_create(parent = monthModeId, mtree_id = mtreeId, typ = "month_cost")
    TariffActions.modifTariffNode_update(id = monthCostId, data = "cost&1000.0%type&1")

    // Канал L2
    //
    tariffIdTreeId = TariffActions.addTariffPlan(used = true)
    TariffActions.updateTariffPlan(tpid = tariffIdTreeId._1, face = 0, title = "Канал L2", title_web = "Канал L2", use_title_in_web = false, values = "", config = "", mask = "", tpused = true)

    // Разовые услуги
    //
    tariffIdTreeId = TariffActions.addTariffPlan(used = true)
    TariffActions.updateTariffPlan(tpid = tariffIdTreeId._1, face = 0, title = "Разовые услуги", title_web = "Разовые услуги", use_title_in_web = false, values = "", config = "", mask = "", tpused = true)
    // Создаем тарифное поддерево модуля rscm
    moduleId = TariffActions.bgBillingModuleId("rscm")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdTreeId._2, 0, now.getMillis).id
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
    tariffIdTreeId = TariffActions.addTariffPlan(used = true)
    TariffActions.updateTariffPlan(tpid = tariffIdTreeId._1, face = 0, title = "Товары", title_web = "Товары", use_title_in_web = false, values = "", config = "", mask = "", tpused = true)
    // Создаем тарифное поддерево модуля rscm
    moduleId = TariffActions.bgBillingModuleId("rscm")
    mtreeId = ModuleTariffTree.create(moduleId, tariffIdTreeId._2, 0, now.getMillis).id
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
  // Справочники -> Группы тарифов
  //
  def tariffGroups(): Unit = {
    /* 1 */ TariffGroup.create(title = "Интернет", tm = 1, df = 0, beh = 0, pos = 0)
    TariffGroupTariff.create(tgid = 1, tpid = 1, date1 = None, date2 = None)
    TariffGroupTariff.create(tgid = 1, tpid = 2, date1 = None, date2 = None)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Договор -> Новый договор
  //
  def contracts(): Unit = {
    NaturalPersons.load()
    LegalEntities.load()
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Договор -> Открыть договор -> ДОГОВОР -> Приход
  //
  def payments(): Unit = {
    Payments.load()
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - шаблоны комментариев
  //
  def contractCommentPatterns(): Unit = {
    /* 1 */ ContractCommentPatterns.create(title = "ФИО + Л/С", pat = "${param_6} ${param_7} ${param_8}, Л/С ${param_2}")
    /* 2 */ ContractCommentPatterns.create(title = "Название + Л/С", pat = "${param_14}, Л/С ${param_2}")
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
      groups = 3,
      mode = 1, // Дебет
      pgid = 1, // Группа параметров: Физ. лицо
      pfid = 0,
      fc = 0, // Физ. лицо
      dtl = 0,
      tgid = "1",
      scrid = "",
      namePattern = "А-${Y2}-${N4}",
      data = Some(data.getBytes),
      patid = 1, // Шаблон комментария
      status = 0, domainid = 1
    )
    /* 2 */ ContractPattern.create(title = "Ф/Л, кредит",
      closesumma = 0.0f, // Лимит
      tpid = "4,5", // Тарифы
      groups = 3,
      mode = 0, // Кредит
      pgid = 1, // Группа параметров: Физ. лицо
      pfid = 0,
      fc = 0, // Физ. лицо
      dtl = 0,
      tgid = "1",
      scrid = "",
      namePattern = "Б-${Y2}-${N4}",
      data = Some(data.getBytes),
      patid = 1, // Шаблон комментария
      status = 0, domainid = 1
    )
    /* 3 */ ContractPattern.create(title = "Ю/Л, аванс, 3 дня",
      closesumma = 0.0f, // Лимит
      tpid = "4,5",
      groups = 5,
      mode = 1, // Дебет
      pgid = 2, // Группа параметров: Юр. лицо
      pfid = 0,
      fc = 1, // Юр. лицо
      dtl = 3, // Время жизни
      tgid = "",
      scrid = "",
      namePattern = "В-${Y2}-${N4}",
      data = Some(data.getBytes),
      patid = 2, // Шаблон комментария
      status = 0, domainid = 3
    )
    /* 4 */ ContractPattern.create(title = "Ю/Л, кредит, лимит -20000 руб.",
      closesumma = -20000.0f, // Лимит
      tpid = "4,5",
      groups = 5,
      mode = 0, // Кредит
      pgid = 2, // Группа параметров: Юр. лицо
      pfid = 0,
      fc = 1, // Юр. лицо
      dtl = 0,
      tgid = "",
      scrid = "",
      namePattern = "Г-${Y2}-${N4}",
      data = Some(data.getBytes),
      patid = 2, // Шаблон комментария
      status = 0, domainid = 3
    )
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Атрибуты -> Атрибуты
  // Справочники -> Атрибуты -> Сущности
  //
  def entitySpecs(): Unit = {
    /* 1 */ EntitySpecAttr.create(title = "Адрес", `type` = 8, comment = "")
    /* 2 */ EntitySpecAttr.create(title = "S/N", `type` = 1, comment = "")
    /* 1 */ EntitySpec.create(title = "Коммутатор", entityspectypeid = 0, comment = "", hidden = 0, entitytitlemacros = "")
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 1, pos = 0)
    EntitySpecAttrLink.create(entityspecid = 1, entityspecattrid = 2, pos = 0)
    /* 2 */ EntitySpec.create(title = "Маршрутизатор", entityspectypeid = 0, comment = "", hidden = 0, entitytitlemacros = "")
    EntitySpecAttrLink.create(entityspecid = 2, entityspecattrid = 1, pos = 0)
    EntitySpecAttrLink.create(entityspecid = 2, entityspecattrid = 2, pos = 0)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Настройка -> Редактор новостей
  //
  def news(): Unit = {
    News.create(gr = 1, dt = DateTime.now, title = "Далекий перигелий: методология и особенности", txt = "Отвесная линия, как бы это ни казалось парадоксальным, иллюстрирует непреложный радиант. Высота дает центральный эффективный диаметp. Уравнение времени ничтожно решает центральный узел, хотя это явно видно на фотогpафической пластинке, полученной с помощью 1.2-метpового телескопа. Метеорный дождь отражает надир. Натуральный логарифм неизменяем.\n\nУ планет-гигантов нет твёрдой поверхности, таким образом экскадрилья отражает тропический год. Угловое расстояние решает космический лимб. Хотя хpонологи не увеpены, им кажется, что орбита меняет далекий Каллисто. Гелиоцентрическое расстояние дает лимб.\n\nУгловое расстояние оценивает вращательный сарос, таким образом, часовой пробег каждой точки поверхности на экваторе равен 1666км. Атомное время последовательно. Большая Медведица, как бы это ни казалось парадоксальным, решает астероидный космический мусор. Большой круг небесной сферы, как бы это ни казалось парадоксальным, оценивает непреложный тропический год (датировка приведена по Петавиусу, Цеху, Хайсу). Магнитное поле точно выбирает зенит.")
    News.create(gr = 1, dt = DateTime.now.minusMonths(1), title = "Спиральный погранслой глазами современников", txt = "Расслоение стабилизирует фотон. Расслоение, вследствие квантового характера явления, стохастично притягивает квазар, даже если пока мы не можем наблюсти это непосредственно. Волна едва ли квантуема.\n\nАтом, даже при наличии сильных аттракторов, стабилизирует экситон в том случае, когда процессы переизлучения спонтанны. Фонон излучает изобарический фронт. Излучение синхронно. Лептон вращает внутримолекулярный кристалл. Неоднородность когерентно заряжает электрон без обмена зарядами или спинами.\n\nЭкситон стохастично облучает межатомный лазер. Кварк мгновенно заряжает тангенциальный атом при любом агрегатном состоянии среды взаимодействия. Погранслой ненаблюдаемо синхронизует лазер, поскольку любое другое поведение нарушало бы изотропность пространства. Туманность воспроизводима в лабораторных условиях.")
  }

}
