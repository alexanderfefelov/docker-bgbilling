package modules

import com.github.alexanderfefelov.bgbilling.api.action.kernel.TariffModule
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import org.joda.time.DateTime
import scalaxb._

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
    ContractPaymentTypes.create(title = "Банковский перевод", up = 0, `type` = 0, flag = 1)
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
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - значения списков -> Значения списков
  //
  def сontractParameterType7Values(): Unit = {
    ContractParameterType7Values.create(pid = 9, title = "Паспорт гражданина РФ")
    ContractParameterType7Values.create(pid = 9, title = "Удостоверение личности военнослужащего РФ")
    ContractParameterType7Values.create(pid = 9, title = "Военный билет")
    ContractParameterType7Values.create(pid = 9, title = "Временное удостоверение личности гражданина РФ")

    ContractParameterType7Values.create(pid = 16, title = "ООО")
    ContractParameterType7Values.create(pid = 16, title = "ЗАО")
    ContractParameterType7Values.create(pid = 16, title = "ПАО")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Домены
  //
  def domains(): Unit = {
    /* 1 */ Domain.create(parentid = 0, title = "foo", comment = "")
    /* 2 */ Domain.create(parentid = 0, title = "bar", comment = "")
    /* 3 */ Domain.create(parentid = 2, title = "baz", comment = "")
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Тарифные планы
  //
  def tariffs(): Unit = {
    case class TwoIds(var id1: Long, var id2: Long) { // https://stackoverflow.com/a/6196814/9152483
      def update(ab: (Long, Long)): Unit = {
        id1 = ab._1
        id2 = ab._2
      }
    }

    var tariffIdtreeId = TariffModule.addTariffPlan(used = 1)
    TariffModule.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Интернет-1", title_web = "Интернет-1", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)
    var moduleId = TariffModule.bgBillingModuleId("inet")
    // Создаем тарифное поддерево модуля inet
    /* 1 */ TariffModule.createMtree(mid = moduleId, parent_tree = 0, tree = tariffIdtreeId._2)
    var mtreeId = 1 // createMtree не возвращает идентификатор созданного объекта
    var rootId = TariffModule.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Добавляем типы трафика
    var trafficTypeId = TariffModule.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "trafficType")
    TariffModule.modifTariffNode_update(id = trafficTypeId, data = "trafficTypeId&0,1,2")
    // Добавляем услугу
    var serviceSetId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "serviceSet")
    TariffModule.modifTariffNode_update(id = serviceSetId, data = "serviceId&1")
    // Добавляем стоимости трафика
    var costId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffModule.modifTariffNode_update(id = costId, data = "type&3%col&1%cost&0.0")
    costId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffModule.modifTariffNode_update(id = costId, data = "type&6%col&1%cost&0.0")
    // Добавляем опции
    var optionId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffModule.modifTariffNode_update(id = optionId, data = "inetOptionId&2")
    optionId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffModule.modifTariffNode_update(id = optionId, data = "inetOptionId&6")
    // Создаем тарифное поддерево модуля npay
    moduleId = TariffModule.bgBillingModuleId("npay")
    /* 2 */ TariffModule.createMtree(mid = moduleId, parent_tree = 0, tree = tariffIdtreeId._2)
    mtreeId = 2 // createMtree не возвращает идентификатор созданного объекта
    rootId = TariffModule.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем помесячную абонентскую плату
    var monthModeId = TariffModule.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "month_mode")
    TariffModule.modifTariffNode_update(id = monthModeId, data = "mode&month%sid&2")
    // Добавляем стоимость
    var monthCostId = TariffModule.modifTariffNode_create(parent = monthModeId, mtree_id = mtreeId, typ = "month_cost")
    TariffModule.modifTariffNode_update(id = monthCostId, data = "cost&500.0%type&1")
    // Создаем тарифное поддерево модуля rscm
    moduleId = TariffModule.bgBillingModuleId("rscm")
    /* 3 */ TariffModule.createMtree(mid = moduleId, parent_tree = 0, tree = tariffIdtreeId._2)
    mtreeId = 3 // createMtree не возвращает идентификатор созданного объекта
    rootId = TariffModule.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем услугу
    var serviceId = TariffModule.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffModule.modifTariffNode_update(id = serviceId, data = "3")
    // Добавляем стоимость
    costId = TariffModule.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "cost")
    TariffModule.modifTariffNode_update(id = costId, data = "col&1%cost&100.0")

    tariffIdtreeId = TariffModule.addTariffPlan(used = 1)
    TariffModule.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Интернет-2", title_web = "Интернет-2", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)
    moduleId = TariffModule.bgBillingModuleId("inet")
    // Создаем тарифное поддерево модуля inet
    /* 4 */ TariffModule.createMtree(mid = moduleId, parent_tree = 0, tree = tariffIdtreeId._2)
    mtreeId = 4 // createMtree не возвращает идентификатор созданного объекта
    rootId = TariffModule.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Добавляем типы трафика
    trafficTypeId = TariffModule.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "trafficType")
    TariffModule.modifTariffNode_update(id = trafficTypeId, data = "trafficTypeId&0,1,2")
    // Добавляем услугу
    serviceSetId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "serviceSet")
    TariffModule.modifTariffNode_update(id = serviceSetId, data = "serviceId&1")
    // Добавляем стоимости трафика
    costId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffModule.modifTariffNode_update(id = costId, data = "type&3%col&1%cost&0.0")
    costId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "cost")
    TariffModule.modifTariffNode_update(id = costId, data = "type&6%col&1%cost&0.0")
    // Добавляем опции
    optionId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffModule.modifTariffNode_update(id = optionId, data = "inetOptionId&3")
    optionId = TariffModule.modifTariffNode_create(parent = trafficTypeId, mtree_id = mtreeId, typ = "optionAdd")
    TariffModule.modifTariffNode_update(id = optionId, data = "inetOptionId&5")
    // Создаем тарифное поддерево модуля npay
    moduleId = TariffModule.bgBillingModuleId("npay")
    /* 5 */ TariffModule.createMtree(mid = moduleId, parent_tree = 0, tree = tariffIdtreeId._2)
    mtreeId = 5 // createMtree не возвращает идентификатор созданного объекта
    rootId = TariffModule.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем помесячную абонентскую плату
    monthModeId = TariffModule.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "month_mode")
    TariffModule.modifTariffNode_update(id = monthModeId, data = "mode&month%sid&2")
    // Добавляем стоимость
    monthCostId = TariffModule.modifTariffNode_create(parent = monthModeId, mtree_id = mtreeId, typ = "month_cost")
    TariffModule.modifTariffNode_update(id = monthCostId, data = "cost&1000.0%type&1")
    // Создаем тарифное поддерево модуля rscm
    moduleId = TariffModule.bgBillingModuleId("rscm")
    /* 6 */ TariffModule.createMtree(mid = moduleId, parent_tree = 0, tree = tariffIdtreeId._2)
    mtreeId = 6 // createMtree не возвращает идентификатор созданного объекта
    rootId = TariffModule.modifTariffNode_create(parent = 0, mtree_id = mtreeId, typ = "root")
    // Создаем услугу
    serviceId = TariffModule.modifTariffNode_create(parent = rootId, mtree_id = mtreeId, typ = "service")
    TariffModule.modifTariffNode_update(id = serviceId, data = "3")
    // Добавляем стоимость
    costId = TariffModule.modifTariffNode_create(parent = serviceId, mtree_id = mtreeId, typ = "cost")
    TariffModule.modifTariffNode_update(id = costId, data = "col&1%cost&100.0")

    tariffIdtreeId = TariffModule.addTariffPlan(used = 1)
    TariffModule.updateTariffPlan(tpid = tariffIdtreeId._1, face = 0, title = "Канал L2", title_web = "Канал L2", use_title_in_web = 0, values = "", config = "", mask = "", tpused = 1)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Справочники -> Другие -> Договоры - шаблоны комментариев
  //
  def contractCommentPatterns(): Unit = {
    /* 1 */ ContractCommentPatterns.create(title = "ФИО", pat = "${param_6} ${param_7} ${param_8}")
    /* 2 */ ContractCommentPatterns.create(title = "Название", pat = "${param_14} ${param_16}")
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
    ContractPattern.create(title = "Ф/Л, дебет",
      closesumma = 0.0f, // Лимит
      tpid = "", groups = 0,
      mode = 1, // Дебет
      pgid = 1, // Группа параметров: Физ. лицо
      pfid = 0,
      fc = 0, // Физ. лицо
      dtl = 0, tgid = "", scrid = "", namePattern = "А-${Y2}-${N6}", data = Some(data.getBytes),
      patid = 1, // Шаблон комментария
      status = 0, domainid = 1
    )
    ContractPattern.create(title = "Ю/Л, кредит",
      closesumma = 0.0f, // Лимит
      tpid = "", groups = 0,
      mode = 0, // Кредит
      pgid = 2, // Группа параметров: Юр. лицо
      pfid = 0,
      fc = 1, // Юр. лицо
      dtl = 0, tgid = "", scrid = "", namePattern = "Б-${Y2}-${N6}", data = Some(data.getBytes),
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
