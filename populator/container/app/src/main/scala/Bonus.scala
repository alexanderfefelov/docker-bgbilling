import com.github.alexanderfefelov.bgbilling.api.soap.kernel.{PluginItem, PlugincfgService}

import scala.concurrent.Await
import scala.concurrent.duration._

object Bonus {

  private def pluginId = 2

  //--------------------------------------------------------------------------------------------------------------------
  // Плагины -> Настройки плагинов
  //
  def plugin(plugincfgService: PlugincfgService) = {
    val cfg = ""
    val responseFuture = plugincfgService.updatePlugin(Some(PluginItem(config = Some(cfg), enabled = true, id = pluginId)))
    Await.result(responseFuture, 60.seconds)
  }

}
