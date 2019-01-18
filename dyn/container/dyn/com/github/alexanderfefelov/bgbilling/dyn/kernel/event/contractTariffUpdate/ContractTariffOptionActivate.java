package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.contractTariffUpdate;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.common.BGException;
import ru.bitel.bgbilling.kernel.container.managed.ServerContext;
import ru.bitel.bgbilling.kernel.event.Event;
import ru.bitel.bgbilling.kernel.event.events.ContractTariffUpdateEvent;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.kernel.tariff.option.common.service.TariffOptionService;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

abstract public class ContractTariffOptionActivate extends EventScriptBase implements Loggable {

    @Override
    public void onEvent(Event event, Setup setup, ConnectionSet connectionSet) throws Exception {
        ContractTariffUpdateEvent richEvent = (ContractTariffUpdateEvent) event;
        logger().info("onContractTariffUpdateEvent: contractId: " + richEvent.getContractId() + "; tariffOptionId: " + getTariffOptionId() + "; activationModeId: " + getActivationModeId());
        if (richEvent.isAddTariff()) {
            try {
                int MODULE_ID = 0;
                ServerContext context = ServerContext.get();
                TariffOptionService tariffOptionService = context.getService(TariffOptionService.class, MODULE_ID);
                tariffOptionService.contractTariffOptionActivate(richEvent.getContractId(), getTariffOptionId(), getActivationModeId(), false);
            } catch (BGException bge) {
                logger().error(bge.getMessage());
            }
        }
    }

    abstract int getTariffOptionId();
    abstract int getActivationModeId();

}
