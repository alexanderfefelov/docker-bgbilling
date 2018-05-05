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
    val moduleIdFuture = moduleService.moduleAdd(Some("rscm"), Some("Разовые услуги"))
    val moduleId = Await.result(moduleIdFuture, 60.seconds)
    DbService.create("Подключение", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    DbService.create("Выезд технического специалиста", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    moduleId
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  def scheduledTasks() = {
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 0, min = 0, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "ru.bitel.bgbilling.modules.rscm.server.Calculator", moduleId = "3", comment = "", params = "mid = 3")
  }

}
