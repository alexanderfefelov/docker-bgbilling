import java.nio.charset.Charset

import better.files.File
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.db.util.Db
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import modules._
import org.joda.time.DateTime
import plugins._
import scalaxb._

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
  Kernel.domains()
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
  Kernel.tariffs()
  Kernel.contracts()
  Kernel.contractComments()
  Kernel.payments()

  println("Finished. Press Ctrl+C")

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Настройка -> Конфигурация
  // Модули -> ЭКЗЕМПЛЯР_МОДУЛЯ -> Конфигурация модуля
  //
  private def moduleConfigs(): Unit = {
    val modules = Seq(
      /* 0 */ "kernel",
      /* 1 */ "inet",
      /* 2 */ "npay",
      /* 3 */ "rscm",
      /* 4 */ "subscription",
      /* 5 */ "bill",
      /* 6 */ "moneta",
      /* 7 */ "qiwi",
      /* 8 */ "card",
      /* 9 */ "mps"
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

    class Cake extends ModuleServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("module-service"))
    }
    val service = new Cake().service

    /* 1 */ Inet.moduleAndServices(service)
    /* 2 */ Npay.moduleAndServices(service)
    /* 3 */ Rscm.moduleAndServices(service)
    /* 4 */ Subscription.moduleAndServices(service)
    /* 5 */ Bill.moduleAndServices(service)
    /* 6 */ Moneta.moduleAndServices(service)
    /* 7 */ Qiwi.moduleAndServices(service)
    /* 8 */ Card.moduleAndServices(service)
    /* 9 */ Mps.moduleAndServices(service)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Плагины -> Настройки плагинов
  //
  private def plugins(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._

    class Cake extends PlugincfgServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("plugincfg-service"))
    }
    val service = new Cake().service

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
