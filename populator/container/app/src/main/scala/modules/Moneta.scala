package modules

import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Moneta {

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    val moduleIdFuture = moduleService.moduleAdd(Some("moneta"), Some("Moneta"))
    Await.result(moduleIdFuture, 30.minutes)
  }

}
