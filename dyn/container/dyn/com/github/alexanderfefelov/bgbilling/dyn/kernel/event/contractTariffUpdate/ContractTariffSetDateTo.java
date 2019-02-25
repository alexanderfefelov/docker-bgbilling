package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.contractTariffUpdate;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.common.BGException;
import ru.bitel.bgbilling.kernel.container.managed.ServerContext;
import ru.bitel.bgbilling.kernel.contract.api.common.bean.ContractTariff;
import ru.bitel.bgbilling.kernel.contract.api.common.service.ContractTariffService;
import ru.bitel.bgbilling.kernel.event.Event;
import ru.bitel.bgbilling.kernel.event.events.ContractTariffUpdateEvent;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.kernel.tariff.common.bean.TariffPlan;
import ru.bitel.bgbilling.kernel.tariff.common.service.TariffService;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class ContractTariffSetDateTo extends EventScriptBase implements Loggable {

    @Override
    public void onEvent(Event event, Setup setup, ConnectionSet connectionSet) throws Exception {
        ContractTariffUpdateEvent richEvent = (ContractTariffUpdateEvent) event;
        logger().info("ContractTariffSetDateTo.onEvent: contractId: " + richEvent.getContractId());
        if (richEvent.isAddTariff()) {
            try {
                int MODULE_ID = 0;
                ServerContext context = ServerContext.get();
                ContractTariffService contractTariffService = context.getService(ContractTariffService.class, MODULE_ID);
                int contractTariffPlanId = richEvent.getContractTariff().getId();
                ContractTariff contractTariff = contractTariffService.contractTariffGet(contractTariffPlanId);
                int tariffPlanId = contractTariff.getTariffPlanId();
                TariffService tariffService = context.getService(TariffService.class, MODULE_ID);
                TariffPlan tariffPlan = tariffService.tariffPlanGet(tariffPlanId);
                Properties properties = new Properties();
                properties.load(new StringReader(tariffPlan.getConfig()));
                String daysValidStr = properties.getProperty("daysValid");
                if (daysValidStr != null) {
                    int daysValid = Integer.parseInt(daysValidStr);
                    Date contractTariffDateFrom = contractTariff.getDateFrom();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(contractTariffDateFrom);
                    calendar.add(Calendar.DATE, daysValid);
                    Date contractTariffDateTo = calendar.getTime();
                    contractTariff.setDateTo(contractTariffDateTo);
                    contractTariffService.contractTariffUpdate(contractTariff);
                }
            } catch (NumberFormatException nfe) {
                // Something went wrong
            } catch (IOException ioe) {
                // Something went wrong
            } catch (BGException bge) {
                // Something went wrong
            }
        }
    }

}
