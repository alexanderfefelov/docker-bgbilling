package plugins

import com.github.alexanderfefelov.bgbilling.api.db.repository.ScheduledTasks
import com.github.alexanderfefelov.bgbilling.api.soap.kernel.{PluginItem, PlugincfgService}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object HelpDesk {

  private def pluginId = 5

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
  }

}
