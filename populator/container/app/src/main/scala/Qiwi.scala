import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._

object Qiwi {

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    val moduleIdFuture = moduleService.moduleAdd(Some("qiwi"), Some("Qiwi"))
    val moduleId = Await.result(moduleIdFuture, 60.seconds)
    moduleId
  }

}
