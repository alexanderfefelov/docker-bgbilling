package com.github.alexanderfefelov.bgbilling.dyn.kernel.event;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.kernel.event.Event;
import ru.bitel.bgbilling.kernel.event.events.*;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.logging.NestedContext;
import ru.bitel.common.sql.ConnectionSet;

public class JunoEventHandler extends EventScriptBase implements Loggable {

    @Override
    public void onEvent(Event event, Setup setup, ConnectionSet connectionSet) throws Exception {
        try {
            NestedContext.push(LOG_CONTEXT);
            switch (CLAZZ.valueOf(event.getClass().getSimpleName())) {
                case CancelTariffEvent:
                    onCancelTariffEvent((CancelTariffEvent) event, setup, connectionSet);
                    break;
                case ChangeTariffByTaskEvent:
                    onChangeTariffByTaskEvent((ChangeTariffByTaskEvent) event, setup, connectionSet);
                    break;
                case ContractTariffDeleteEvent:
                    onContractTariffDeleteEvent((ContractTariffDeleteEvent) event, setup, connectionSet);
                    break;
                case ContractTariffUpdateEvent:
                    onContractTariffUpdateEvent((ContractTariffUpdateEvent) event, setup, connectionSet);
                    break;
                default:
                    break;
            }
        } finally {
            NestedContext.pop();
        }
    }

    private void onCancelTariffEvent(CancelTariffEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().info("onEvent: " + event.toString() + "; contractTariffId: " + event.getContractTariff().getId());
    }

    private void onChangeTariffByTaskEvent(ChangeTariffByTaskEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().info("onEvent: " + event.toString() + "; from contractTariffId: " + event.getFromTariff().getId() + " to tariffPlanId: " + event.getToTariffPlan().getId());
    }

    private void onContractTariffDeleteEvent(ContractTariffDeleteEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().info("onEvent: " + event.toString() + "; contractTariffId: " + event.getContractTariffId());
    }

    private void onContractTariffUpdateEvent(ContractTariffUpdateEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().info("onEvent: " + event.toString() + "; isAddTariff: " + event.isAddTariff() + "; contractTariffId: " + event.getContractTariff().getId());
    }

    enum CLAZZ {
        CancelTariffEvent,
        ChangeTariffByTaskEvent,
        ContractTariffDeleteEvent,
        ContractTariffUpdateEvent
    }

    private static final String LOG_CONTEXT = "juno";

}
