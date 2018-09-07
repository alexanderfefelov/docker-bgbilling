package plugins

import com.github.alexanderfefelov.bgbilling.api.db.repository.ScheduledTasks
import com.github.alexanderfefelov.bgbilling.api.soap.kernel.{PluginItem, PlugincfgService}

import scala.concurrent.Await
import scala.concurrent.duration._

object Dispatch {

  private def pluginId = 4

  //--------------------------------------------------------------------------------------------------------------------
  // Плагины -> Настройки плагинов
  //
  def plugin(plugincfgService: PlugincfgService) = {
    val cfg = ""
    val responseFuture = plugincfgService.updatePlugin(Some(PluginItem(config = Some(cfg), enabled = true, id = pluginId)))
    Await.result(responseFuture, 30.minutes)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  def scheduledTasks() = {
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 0, min = 0, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "ru.bitel.bgbilling.plugins.dispatch.server.task.DispatchWorker", moduleId = s"p$pluginId", comment = "Внимание! Эта задача должна выполняться каждую минуту.", params = "")
  }

}
