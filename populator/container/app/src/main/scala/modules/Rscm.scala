package modules

import com.github.alexanderfefelov.bgbilling.api.db.repository.{ScheduledTasks, Service => DbService}
import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._

object Rscm {

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    val moduleIdFuture = moduleService.moduleAdd(Some("rscm"), Some("Товары и разовые услуги"))
    val moduleId = Await.result(moduleIdFuture, 10.minutes)
    /* 4 */ DbService.create("Подключение", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    /* 5 */ DbService.create("Настройка бытового Wi-Fi-маршрутизатора", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    /* 6 */ DbService.create("Выезд технического специалиста", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    /* 7 */ DbService.create("Маршрутизатор MikroTik hAP ac2 (RBD52G-5HacD2HnD-TC)", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    /* 8 */ DbService.create("Маршрутизатор TP-Link Archer C1200", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    /* 9 */ DbService.create("Маршрутизатор Asus RT-AC53", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    moduleId
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  def scheduledTasks() = {
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 0, min = 0, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "ru.bitel.bgbilling.modules.rscm.server.Calculator", moduleId = "3", comment = "", params = "mid=3")
  }

}
