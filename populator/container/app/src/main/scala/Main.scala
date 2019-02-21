import java.nio.charset.Charset

import better.files._
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import com.github.alexanderfefelov.bgbilling.api.db.util.Db
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import com.typesafe.config.{ConfigFactory, ConfigParseOptions, ConfigRenderOptions, ConfigSyntax}
import loaders._
import modules._
import org.joda.time.{DateTime, Seconds}
import plugins._
import scalaxb._
import scalikejdbc._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

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

  val config = ConfigFactory.parseFile(new java.io.File("src/main/resources/loaders.conf")).resolve()
  val json = config.root().render(ConfigRenderOptions.concise())

  Db.init()

  execute("Kernel.dynamicCodeRecompile", Kernel.dynamicCodeRecompile())
  execute("moduleConfigs", moduleConfigs())
  execute("Kernel.users", Kernel.users(json))
  execute("Kernel.bgsGroups", Kernel.bgsGroups())
  execute("Kernel.addresses", Kernel.addresses(json))
  execute("Kernel.contractGroups", Kernel.contractGroups())
  execute("Kernel.contractPaymentTypes", Kernel.contractPaymentTypes(json))
  execute("Kernel.contractChargeTypes", Kernel.contractChargeTypes(json))
  execute("Kernel.contractParametersPrefs", Kernel.contractParametersPrefs())
  execute("Kernel.contractParameterGroups", Kernel.contractParameterGroups())
  execute("Kernel.contractParameterType7Values", Kernel.contractParameterType7Values(json))
  execute("Kernel.domains", Kernel.domains(json))
  execute("Kernel.contractCommentPatterns", Kernel.contractCommentPatterns())
  execute("Kernel.contractPatterns", Kernel.contractPatterns())
  execute("Kernel.entitySpecs", Kernel.entitySpecs())
  execute("Kernel.news", Kernel.news())
  execute("modulesAndServices", modulesAndServices())
  execute("plugins", plugins())
  execute("scheduledTasks", scheduledTasks())

  execute("alterTables", alterTables())

  execute("Kernel.eventHandlers", Kernel.eventHandlers())
  execute("Kernel.globalScripts", Kernel.globalScripts())
  execute("Kernel.sqlTemplates", Kernel.sqlTemplates())

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
  execute("Kernel.tariffLabels", Kernel.tariffLabels())
  execute("Kernel.tariffOptions", Kernel.tariffOptions())
  execute("Kernel.tariffs", Kernel.tariffs())
  execute("Kernel.tariffGroups", Kernel.tariffGroups())
  execute("Kernel.contracts", Kernel.contracts(json))
  execute("Kernel.payments", Kernel.payments(json))

  execute("Rscm.transactions", Rscm.transactions())
  execute("Kernel.contractStatuses", Kernel.contractStatuses())
  execute("Kernel.contractTariffs", Kernel.contractTariffs())

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
    def makeConfigActive(i: Int, id: Int) = {
      import com.github.alexanderfefelov.bgbilling.api.soap.kernel._

      class ModuleConfigCake extends ModuleConfigServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
        override def baseAddress = new java.net.URI(soapServiceBaseAddress("module-config-service"))
      }
      val moduleConfigService = new ModuleConfigCake().service

      val responseFuture = moduleConfigService.setActive(i, id)
      Await.result(responseFuture, 5.minutes)
    }

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
      makeConfigActive(i, id)
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  //
  private def modulesAndServices(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._

    class ModuleCake extends ModuleServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("module-service"))
    }
    val moduleService = new ModuleCake().service

    /* 1 */ Inet.moduleAndServices(moduleService)
    /* 2 */ Npay.moduleAndServices(moduleService)
    /* 3 */ Rscm.moduleAndServices(moduleService)
    /* 4 */ Subscription.moduleAndServices(moduleService)
    /* 5 */ Bill.moduleAndServices(moduleService)
    /* 6 */ Moneta.moduleAndServices(moduleService)
    /* 7 */ Qiwi.moduleAndServices(moduleService)
    /* 8 */ Card.moduleAndServices(moduleService)
    /* 9 */ Mps.moduleAndServices(moduleService)
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Плагины -> Настройки плагинов
  //
  private def plugins(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.kernel._

    class PlugincfgCake extends PlugincfgServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("plugincfg-service"))
    }
    val plugincfgService = new PlugincfgCake().service

    Bonus.plugin(plugincfgService)
    Dispatch.plugin(plugincfgService)
    HelpDesk.plugin(plugincfgService)
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
    HelpDesk.scheduledTasks()
  }

}
