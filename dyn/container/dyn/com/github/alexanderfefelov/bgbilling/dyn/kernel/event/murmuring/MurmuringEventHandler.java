package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.murmuring;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.common.BGException;
import ru.bitel.bgbilling.kernel.container.managed.ServerContext;
import ru.bitel.bgbilling.kernel.contract.api.common.event.*;
import ru.bitel.bgbilling.kernel.contract.balance.server.event.*;
import ru.bitel.bgbilling.kernel.event.Event;
import ru.bitel.bgbilling.kernel.event.events.*;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.kernel.tariff.option.common.service.TariffOptionService;
import ru.bitel.bgbilling.kernel.tariff.option.server.event.*;
import ru.bitel.bgbilling.modules.rscm.server.event.*;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.logging.NestedContext;
import ru.bitel.common.sql.ConnectionSet;

public class MurmuringEventHandler extends EventScriptBase implements Loggable {

    @Override
    public void onEvent(Event event, Setup setup, ConnectionSet set) throws Exception {
        try {
            NestedContext.push(LOG_CONTEXT);
            switch (CLAZZ.valueOf(event.getClass().getSimpleName())) {

                // ru.bitel.bgbilling.kernel.event.events
                //

                case CalculateEvent:
                    onCalculateEvent((CalculateEvent) event);
                    break;
                case CancelTariffEvent:
                    onCancelTariffEvent((CancelTariffEvent) event);
                    break;
                case ChangeContractLimitEvent:
                    onChangeContractLimitEvent((ChangeContractLimitEvent) event);
                    break;
                case ChangeTariffByTaskEvent:
                    onChangeTariffByTaskEvent((ChangeTariffByTaskEvent) event);
                    break;
                case ContractLimitUserLow:
                    onContractLimitUserLow((ContractLimitUserLow) event);
                    break;
                case ContractTariffDeleteEvent:
                    onContractTariffDeleteEvent((ContractTariffDeleteEvent) event);
                    break;
                case ContractTariffUpdateEvent:
                    onContractTariffUpdateEvent((ContractTariffUpdateEvent) event);
                    break;
                case ContractWebLoginEvent:
                    onContractWebLoginEvent((ContractWebLoginEvent) event);
                    break;
                case GetChangeTariffDatesEvent:
                    onGetChangeTariffDatesEvent((GetChangeTariffDatesEvent) event);
                    break;
                case GetContractCardsList:
                    onGetContractCardsList((GetContractCardsList) event);
                    break;
                case GetTariffListEvent:
                    onGetTariffListEvent((GetTariffListEvent) event);
                    break;
                case LimitChangedEvent:
                    onLimitChangedEvent((LimitChangedEvent) event);
                    break;
                case ServerStartEvent:
                    onServerStartEvent((ServerStartEvent) event);
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

                // ru.bitel.bgbilling.kernel.contract.balance.server.event
                //

                case PaymentEvent:
                    onPaymentEvent((PaymentEvent) event);
                    break;

                // ru.bitel.bgbilling.modules.rscm.server.event
                //

                case RSCMContractServiceUpdateEvent:
                    onRSCMContractServiceUpdateEvent((RSCMContractServiceUpdateEvent) event);
                    break;

                // ru.bitel.bgbilling.kernel.contract.api.common.event
                //

                case ContractPasswordChangedEvent:
                    onContractPasswordChangedEvent((ContractPasswordChangedEvent) event);
                    break;

                // ru.bitel.bgbilling.kernel.tariff.option.server.event
                //

                case TariffOptionListAvailableEvent:
                    onTariffOptionListAvailableEvent((TariffOptionListAvailableEvent) event);
                    break;

                // Default
                //

                default:
                    logger().trace("onEvent (default): " + event.toString());
                    break;
            }
        } catch (IllegalArgumentException iae) { // No enum constant
            logger().trace("onEvent (unknown): " + event.toString());
        } finally {
            NestedContext.pop();
        }
    }

    // ru.bitel.bgbilling.kernel.event.events
    //

    private void onCalculateEvent(CalculateEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onCancelTariffEvent(CancelTariffEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onChangeContractLimitEvent(ChangeContractLimitEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onChangeTariffByTaskEvent(ChangeTariffByTaskEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractLimitUserLow(ContractLimitUserLow event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractTariffDeleteEvent(ContractTariffDeleteEvent event) {
        logger().trace("onEvent: " + event.toString() + "; contractTariffId: " + event.getContractTariffId());
    }

    private void onContractTariffUpdateEvent(ContractTariffUpdateEvent event) {
        logger().trace("onEvent: " + event.toString() + "; isAddTariff: " + event.isAddTariff() + "; contractTariffId: " + event.getContractTariff().getId());
    }

    private void onContractWebLoginEvent(ContractWebLoginEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onGetChangeTariffDatesEvent(GetChangeTariffDatesEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onGetContractCardsList(GetContractCardsList event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onGetTariffListEvent(GetTariffListEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onLimitChangedEvent(LimitChangedEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onServerStartEvent(ServerStartEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onServiceUpdateEvent(ServiceUpdateEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onTimerEvent(TimerEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onValidateTextParamEvent(ValidateTextParamEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.kernel.contract.balance.server.event
    //

    private void onPaymentEvent(PaymentEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.modules.rscm.server.event
    //

    private void onRSCMContractServiceUpdateEvent(RSCMContractServiceUpdateEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.kernel.contract.api.common.event
    //

    private void onContractPasswordChangedEvent(ContractPasswordChangedEvent event) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.kernel.tariff.option.server.event
    //

    private void onTariffOptionListAvailableEvent(TariffOptionListAvailableEvent event) {
        logger().trace("onEvent: " + event.toString());
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
        CalculateEvent,
        CancelTariffEvent,
        ChangeContractLimitEvent,
        ChangeTariffByTaskEvent,
        // ContractAddingSubEvent
        // ContractAddObjectEvent
        // ContractCreatedEvent
        // ContractDeleteObjectEvent
        ContractLimitUserLow,
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
        ContractWebLoginEvent,
        // GetAdditionalActionListEvent
        // GetAdditionalWebActionListEvent
        GetChangeTariffDatesEvent,
        GetContractCardsList,
        // GetContractStatusChangeDatesEvent
        GetTariffListEvent,
        LimitChangedEvent,
        // OnContractWrapEvent
        // PersonalTariffDeleteEvent
        // PersonalTariffTreeUpdateEvent
        // PersonalTariffUpdateEvent
        ServerStartEvent,
        ServiceUpdateEvent,
        TimerEvent,
        ValidateTextParamEvent,

        // ru.bitel.bgbilling.kernel.contract.balance.server.event
        // -------------------------------------------------------
        // ChargeEvent
        // ContractBalanceChangedEvent
        // ConvergenceBalanceEvent
        // PaymentChangingEvent
        // PaymentDeletedEvent
        PaymentEvent,
        // ReserveCloseEvent
        // ReserveEvent

        // ru.bitel.bgbilling.modules.rscm.server.event
        // --------------------------------------------
        RSCMContractServiceUpdateEvent,

        // ru.bitel.bgbilling.kernel.contract.api.common.event
        // ---------------------------------------------------
        // ContractGroupModifiedEvent
        // ContractModifiedEvent
        // ContractParameterGroupAttrModifiedEvent
        // ContractParameterGroupModifiedEvent
        // ContractParameterListItemModifiedEvent
        ContractPasswordChangedEvent,

        // ru.bitel.bgbilling.kernel.tariff.option.server.event
        // ----------------------------------------------------
        // ContractTariffOptionChangedEvent
        // TariffOptionActivatedEvent
        // TariffOptionBeforeActivateEvent
        // TariffOptionDeactivatedEvent
        // TariffOptionDeactivateEvent
        TariffOptionListAvailableEvent
    }

    private static final String LOG_CONTEXT = "murmuring";

}
