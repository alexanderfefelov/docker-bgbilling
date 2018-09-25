package modules

import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Qiwi {

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    val moduleIdFuture = moduleService.moduleAdd(Some("qiwi"), Some("Qiwi"))
    Await.result(moduleIdFuture, 30.minutes)
  }

}
