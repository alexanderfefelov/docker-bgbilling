package modules

import com.github.alexanderfefelov.bgbilling.api.db.repository.{ScheduledTasks, Service => DbService}
import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._

object Kernel {

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  def scheduledTasks() = {
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 16, min = 1048576, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "ru.bitel.bgbilling.kernel.task.server.Validator", moduleId = "0", comment = "Проверка базы данных биллинга на корректность", params = "email=admin@inter.net")
  }

}
