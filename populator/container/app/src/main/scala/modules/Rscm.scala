package modules

import com.github.alexanderfefelov.bgbilling.api.db.repository.{RscmService3, ScheduledTasks, Service => DbService}
import com.github.alexanderfefelov.bgbilling.api.soap.kernel.ModuleService
import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import org.joda.time.DateTime
import scalaxb._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Rscm {

  //--------------------------------------------------------------------------------------------------------------------
  // Модули -> Редактор модулей и услуг
  // Модули -> Товары и разовые услуги
  //
  def moduleAndServices(moduleService: ModuleService): Int = {
    val moduleIdFuture = moduleService.moduleAdd(Some("rscm"), Some("Товары и разовые услуги"))
    val moduleId = Await.result(moduleIdFuture, 30.minutes)
    /* 4 */ DbService.create("Подключение", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    RscmService3.create(Some("4"), Some("шт."))
    /* 5 */ DbService.create("Настройка бытового Wi-Fi-маршрутизатора", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    RscmService3.create(Some("5"), Some("шт."))
    /* 6 */ DbService.create("Выезд технического специалиста", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    RscmService3.create(Some("6"), Some("шт."))
    /* 7 */ DbService.create("Маршрутизатор MikroTik hAP ac2 (RBD52G-5HacD2HnD-TC)", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    RscmService3.create(Some("7"), Some("шт."))
    /* 8 */ DbService.create("Маршрутизатор TP-Link Archer C1200", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    RscmService3.create(Some("8"), Some("шт."))
    /* 9 */ DbService.create("Маршрутизатор Asus RT-AC53", mid = moduleId, parentid = 0, datefrom = None, dateto = None,
      comment = "", description = "", lm = DateTime.now, isusing = Some(true), unit = 10000)
    RscmService3.create(Some("9"), Some("шт."))
    moduleId
  }

  //--------------------------------------------------------------------------------------------------------------------
  // Сервис -> Администрирование -> Планировщик заданий
  //
  def scheduledTasks(): Unit = {
    ScheduledTasks.create(mm = 0, dm = 0, dw = 0, hh = 0, min = 0, prior = 1, date1 = None, date2 = None, status = 1, classId = -1,
      `class` = "ru.bitel.bgbilling.modules.rscm.server.Calculator", moduleId = "3", comment = "", params = "mid=3\n")
  }

  def transactions(): Unit = {
    import com.github.alexanderfefelov.bgbilling.api.soap.rscm._

    class RscmCake extends RSCMServiceBindings with Soap11ClientsWithAuthHeaderAsync with ConfigurableDispatchHttpClientsAsync with ApiSoapConfig {
      override def baseAddress = new java.net.URI(soapServiceBaseAddress("rscm-service"))
    }
    val rscmService = new RscmCake().service

    var rscmContractService = RscmContractService(serviceId = 4, attributes = Map(
      "id"         -> dr("id", 0),
      "contractId" -> dr("contractId", 100),
      "date"       -> dr("date", "2018-09-01T00:00:00+03:00"),
      "amount"     -> dr("amount", 1),
      "comment"    -> dr("comment", "")
    ))
    var responseFuture = rscmService.updateRSCMContractService(Some(rscmContractService))
    rscmContractService = RscmContractService(serviceId = 7, attributes = Map(
      "id"         -> dr("id", 0),
      "contractId" -> dr("contractId", 100),
      "date"       -> dr("date", "2018-10-01T00:00:00+03:00"),
      "amount"     -> dr("amount", 2),
      "comment"    -> dr("comment", "")
    ))
    responseFuture = rscmService.updateRSCMContractService(Some(rscmContractService))
    Await.result(responseFuture, 15.seconds)
    rscmContractService = RscmContractService(serviceId = 5, attributes = Map(
      "id"         -> dr("id", 0),
      "contractId" -> dr("contractId", 100),
      "date"       -> dr("date", "2018-10-01T00:00:00+03:00"),
      "amount"     -> dr("amount", 2),
      "comment"    -> dr("comment", "")
    ))
    responseFuture = rscmService.updateRSCMContractService(Some(rscmContractService))
    Await.result(responseFuture, 15.seconds)
  }

}
