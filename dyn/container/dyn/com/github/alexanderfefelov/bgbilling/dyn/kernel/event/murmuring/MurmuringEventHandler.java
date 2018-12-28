package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.murmuring;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.kernel.event.Event;
import ru.bitel.bgbilling.kernel.event.events.*;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.logging.NestedContext;
import ru.bitel.common.sql.ConnectionSet;

public class MurmuringEventHandler extends EventScriptBase implements Loggable {

    @Override
    public void onEvent(Event event, Setup setup, ConnectionSet set) throws Exception {
        NestedContext.push("murmuring");
        try {
            switch (CLAZZ.valueOf(event.getClass().getSimpleName())) {
                case ContractTariffDeleteEvent:
                    onContractTariffDeleteEvent((ContractTariffDeleteEvent) event);
                    break;
                case ContractTariffUpdateEvent:
                    onContractTariffUpdateEvent((ContractTariffUpdateEvent) event);
                    break;
                case ServiceUpdateEvent:
                    onServiceUpdateEvent((ServiceUpdateEvent) event);
                    break;
                case TimerEvent:
                    onTimerEvent((TimerEvent) event);
                    break;
                case ValidateTextParamEvent:
                    onValidateTextParamEvent((ValidateTextParamEvent) event);
                    break;
                default:
                    logger().trace("onEvent: " + event.toString());
                    break;
            }
        } finally {
            NestedContext.pop();
        }
    }

    private void onContractTariffDeleteEvent(ContractTariffDeleteEvent event) {
    }

    private void onContractTariffUpdateEvent(ContractTariffUpdateEvent event) {
    }

    private void onServiceUpdateEvent(ServiceUpdateEvent event) {
    }

    private void onTimerEvent(TimerEvent event) {
    }

    private void onValidateTextParamEvent(ValidateTextParamEvent event) {
    }

    enum CLAZZ {
        // ru.bitel.bgbilling.kernel.event.events
        // --------------------------------------
        // ActionAfterEvent
        // ActionBeforeEvent
        // ActionEvent
        // AdditionalActionEvent
        // AppsEvent
        // BeforeServiceDeleteEvent
        // CalculateEvent
        // CancelTariffEvent
        // ChangeContractLimitEvent
        // ChangeTariffByTaskEvent
        // ContractAddingSubEvent
        // ContractAddObjectEvent
        // ContractCreatedEvent
        // ContractDeleteObjectEvent
        // ContractLimitUserLow
        // ContractObjectParameterBeforeUpdateEvent
        // ContractObjectParameterUpdateEvent
        // ContractParamBeforeChangeEvent
        // ContractParamChangedEvent
        // ContractServicesChangedEvent
        // ContractSetStatusLogicEvent
        // ContractStatusChangedEvent
        // ContractStatusChangedTopicEvent
        // ContractStatusChangingEvent
        // ContractStatusModifiedEvent
        ContractTariffDeleteEvent,
        ContractTariffUpdateEvent,
        // ContractUpdateObjectEvent
        // ContractWebLoginEvent
        // GetAdditionalActionListEvent
        // GetAdditionalWebActionListEvent
        // GetChangeTariffDatesEvent
        // GetContractCardsList
        // GetContractStatusChangeDatesEvent
        // GetTariffListEvent
        // LimitChangedEvent
        // OnContractWrapEvent
        // PersonalTariffDeleteEvent
        // PersonalTariffTreeUpdateEvent
        // PersonalTariffUpdateEvent
        // ServerStartEvent
        ServiceUpdateEvent,
        TimerEvent,
        ValidateTextParamEvent
    }

}
