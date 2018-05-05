package modules

import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService

import scala.concurrent.Await
import scala.concurrent.duration._

object Bill {

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    val moduleIdFuture = moduleService.moduleAdd(Some("bill"), Some("Счета"))
    Await.result(moduleIdFuture, 60.seconds)
  }

}
