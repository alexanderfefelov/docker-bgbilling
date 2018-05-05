import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService

import scala.concurrent.Await
import scala.concurrent.duration._

object Mps {

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    val moduleIdFuture = moduleService.moduleAdd(Some("mps"), Some("MPS"))
    Await.result(moduleIdFuture, 60.seconds)
  }

}
