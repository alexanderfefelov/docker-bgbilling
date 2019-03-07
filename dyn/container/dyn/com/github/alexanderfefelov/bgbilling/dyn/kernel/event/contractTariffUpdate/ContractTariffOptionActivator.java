package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.contractTariffUpdate;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.common.BGException;
import ru.bitel.bgbilling.kernel.container.managed.ServerContext;
import ru.bitel.bgbilling.kernel.event.events.ContractTariffUpdateEvent;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.kernel.tariff.option.common.service.TariffOptionService;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

abstract public class ContractTariffOptionActivator extends EventScriptBase<ContractTariffUpdateEvent> implements Loggable {

    @Override
    public void onEvent(ContractTariffUpdateEvent event, Setup setup, ConnectionSet connectionSet) throws Exception {
        logger().info("onEvent: contractId: " + event.getContractId() + "; tariffOptionId: " + getTariffOptionId() + "; activationModeId: " + getActivationModeId());
        if (event.isAddTariff()) {
            try {
                int MODULE_ID = 0;
                ServerContext context = ServerContext.get();
                TariffOptionService tariffOptionService = context.getService(TariffOptionService.class, MODULE_ID);
                tariffOptionService.contractTariffOptionActivate(event.getContractId(), getTariffOptionId(), getActivationModeId(), false);
            } catch (BGException bge) {
                logger().error(bge.getMessage());
            }
        }
    }

    abstract int getTariffOptionId();
    abstract int getActivationModeId();

}
