import java.nio.charset.Charset

import better.files.File
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.db.util.Db
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import modules._
import org.joda.time.DateTime
import plugins._

import scala.concurrent.Await
import scala.concurrent.duration._
import scalaxb.DispatchHttpClientsAsync

object Main extends App {

  implicit val charset: Charset = Charset.forName("UTF-8")
  val now = DateTime.now()

  Db.init()

  Kernel.dynamicCodeRecompile()
  moduleConfigs()
  Kernel.bgsGroups()
  Kernel.addresses()
  Kernel.contractPaymentTypes()
  Kernel.contractParametersPrefs()
  Kernel.contractParameterGroups()
  Kernel.сontractParameterType7Values()
  Kernel.contractCommentPatterns()
  Kernel.contractPatterns()
  Kernel.entitySpecs()
  modulesAndServices()
  plugins()
  scheduledTasks()
  Inet.deviceTypes()
  Inet.deviceGroups()
  Inet.vlanResources()
  Inet.ipResources()
  Inet.trafficTypes()
  Inet.trafficTypeLinks()
  Inet.options()
  Inet.devices()
  Inet.servTypes()
  Inet.deviceReload()

  println("Finished. Press Ctrl+C")

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Настройка -> Конфигурация
  // Модули -> ЭКЗЕМПЛЯР_МОДУЛЯ -> Конфигурация модуля
  //
  private def moduleConfigs(): Unit = {
    val modules = Seq(
      // Ядро
      "kernel",
      // Модули интернета
      "inet",
      // Модули абонентских плат и разовых услуг
      "npay",
      "rscm",
      "subscription",
      // Модули платежных систем
      "moneta",
      "qiwi",
      "mps",
      // Прочее
      "bill"
    )
    for (i <- modules.indices) {
      ModuleConfig.create(mid = Some(i), dt = now, title = "Default", active = 1, uid = Some(1),
        config = Some(File(s"bgbilling/${modules(i)}.conf").contentAsString)
      )
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  private def modulesAndServices(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class Cake extends ModuleServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("module-service"))
    }
    val cake = new Cake
    val service = cake.service

    Inet.moduleAndServices(service)
    Npay.moduleAndServices(service)
    Rscm.moduleAndServices(service)
    Subscription.moduleAndServices(service)
    Moneta.moduleAndServices(service)
    Qiwi.moduleAndServices(service)
    Mps.moduleAndServices(service)
    Bill.moduleAndServices(service)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Плагины -> Настройки плагинов
  //
  private def plugins(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
    import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._

    class Cake extends PlugincfgServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("plugincfg-service"))
    }
    val cake = new Cake
    val service = cake.service

    Bonus.plugin(service)
    Dispatch.plugin(service)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  private def scheduledTasks() = {
    Kernel.scheduledTasks()
    Npay.scheduledTasks()
    Rscm.scheduledTasks()
    Bonus.scheduledTasks()
    Dispatch.scheduledTasks()
  }

}
