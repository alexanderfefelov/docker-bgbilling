import java.nio.charset.Charset

import better.files._
import com.github.alexanderfefelov.bgbilling.api.action.kernel.ServiceActions
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.db.util.Db
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import loaders._
import modules._
import org.joda.time.{DateTime, Seconds}
import plugins._
import scalaxb._
import scalikejdbc._

object Main extends App {

  implicit val charset: Charset = Charset.forName("UTF-8")
  val now = DateTime.now()

  private def execute(name: String, f: => Unit): Unit = {
    System.out.print(s"$name ")
    System.out.flush()
    val start = DateTime.now()
    f
    val end = DateTime.now()
    val duration = Seconds.secondsBetween(start, end)
    System.out.println(duration)
  }


  Db.init()

  execute("Kernel.dynamicCodeRecompile", Kernel.dynamicCodeRecompile())
  execute("moduleConfigs", moduleConfigs())
  execute("Kernel.users", Kernel.users())
  execute("Kernel.bgsGroups", Kernel.bgsGroups())
  execute("Kernel.addresses", Kernel.addresses())
  execute("Kernel.contractPaymentTypes", Kernel.contractPaymentTypes())
  execute("Kernel.contractChargeTypes", Kernel.contractChargeTypes())
  execute("Kernel.contractParametersPrefs", Kernel.contractParametersPrefs())
  execute("Kernel.contractParameterGroups", Kernel.contractParameterGroups())
  execute("Kernel.contractParameterType7Values", Kernel.contractParameterType7Values())
  execute("Kernel.domains", Kernel.domains())
  execute("Kernel.contractCommentPatterns", Kernel.contractCommentPatterns())
  execute("Kernel.contractPatterns", Kernel.contractPatterns())
  execute("Kernel.entitySpecs", Kernel.entitySpecs())
  execute("modulesAndServices", modulesAndServices())
  execute("plugins", plugins())
  execute("scheduledTasks", scheduledTasks())

  execute("alterTables", alterTables())

  execute("Inet.deviceTypes", Inet.deviceTypes())
  execute("Inet.deviceGroups", Inet.deviceGroups())
  execute("Inet.vlanResources", Inet.vlanResources())
  execute("Inet.ipResources", Inet.ipResources())
  execute("Inet.trafficTypes", Inet.trafficTypes())
  execute("Inet.trafficTypeLinks", Inet.trafficTypeLinks())
  execute("Inet.options", Inet.options())
  execute("Inet.devices", Inet.devices())
  execute("Inet.servTypes", Inet.servTypes())
  execute("Inet.deviceReload", Inet.deviceReload())
  execute("Kernel.tariffs", Kernel.tariffs())
  execute("Kernel.contracts", Kernel.contracts())
  execute("Kernel.payments", Kernel.payments())

  println("Finished. Press Ctrl+C")

  private def alterTables(): Unit = {
    implicit val session = AutoSession
    sql"""alter table inet_device_type_1 modify column comment text""".execute().apply()
  }

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
      val id = ModuleConfig.create(mid = Some(i), dt = now, title = "Default", active = 1, uid = Some(1),
        config = Some(Resource.getAsString(s"bgbilling/${modules(i)}.conf"))).id
      ServiceActions.setModuleConfig(i, id)
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
