package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.murmuring;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.kernel.contract.api.common.event.*;
import ru.bitel.bgbilling.kernel.contract.balance.server.event.*;
import ru.bitel.bgbilling.kernel.event.Event;
import ru.bitel.bgbilling.kernel.event.events.*;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.kernel.tariff.option.server.event.*;
import ru.bitel.bgbilling.modules.rscm.server.event.*;
import ru.bitel.bgbilling.plugins.helpdesk.server.bean.event.TopicWasUpdatedEvent;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.logging.NestedContext;
import ru.bitel.common.sql.ConnectionSet;

public class MurmuringEventHandler extends EventScriptBase implements Loggable {

    @Override
    public void onEvent(Event event, Setup setup, ConnectionSet connectionSet) throws Exception {
        try {
            NestedContext.push(LOG_CONTEXT);
            switch (CLAZZ.valueOf(event.getClass().getSimpleName())) {

                // ru.bitel.bgbilling.kernel.event.events
                //

                case ActionAfterEvent:
                    onActionAfterEvent((ActionAfterEvent) event, setup, connectionSet);
                    break;
                case ActionBeforeEvent:
                    onActionBeforeEvent((ActionBeforeEvent) event, setup, connectionSet);
                    break;
                case AdditionalActionEvent:
                    onAdditionalActionEvent((AdditionalActionEvent) event, setup, connectionSet);
                    break;
                case BeforeServiceDeleteEvent:
                    onBeforeServiceDeleteEvent((BeforeServiceDeleteEvent) event, setup, connectionSet);
                    break;
                case CalculateEvent:
                    onCalculateEvent((CalculateEvent) event, setup, connectionSet);
                    break;
                case CancelTariffEvent:
                    onCancelTariffEvent((CancelTariffEvent) event, setup, connectionSet);
                    break;
                case ChangeContractLimitEvent:
                    onChangeContractLimitEvent((ChangeContractLimitEvent) event, setup, connectionSet);
                    break;
                case ChangeTariffByTaskEvent:
                    onChangeTariffByTaskEvent((ChangeTariffByTaskEvent) event, setup, connectionSet);
                    break;
                case ContractAddingSubEvent:
                    onContractAddingSubEvent((ContractAddingSubEvent) event, setup, connectionSet);
                    break;
                case ContractAddObjectEvent:
                    onContractAddObjectEvent((ContractAddObjectEvent) event, setup, connectionSet);
                    break;
                case ContractCreatedEvent:
                    onContractCreatedEvent((ContractCreatedEvent) event, setup, connectionSet);
                    break;
                case ContractDeleteObjectEvent:
                    onContractDeleteObjectEvent((ContractDeleteObjectEvent) event, setup, connectionSet);
                    break;
                case ContractLimitUserLow:
                    onContractLimitUserLow((ContractLimitUserLow) event, setup, connectionSet);
                    break;
                case ContractParamBeforeChangeEvent:
                    onContractParamBeforeChangeEvent((ContractParamBeforeChangeEvent) event, setup, connectionSet);
                    break;
                case ContractParamChangedEvent:
                    onContractParamChangedEvent((ContractParamChangedEvent) event, setup, connectionSet);
                    break;
                case ContractTariffDeleteEvent:
                    onContractTariffDeleteEvent((ContractTariffDeleteEvent) event, setup, connectionSet);
                    break;
                case ContractTariffUpdateEvent:
                    onContractTariffUpdateEvent((ContractTariffUpdateEvent) event, setup, connectionSet);
                    break;
                case ContractWebLoginEvent:
                    onContractWebLoginEvent((ContractWebLoginEvent) event, setup, connectionSet);
                    break;
                case GetChangeTariffDatesEvent:
                    onGetChangeTariffDatesEvent((GetChangeTariffDatesEvent) event, setup, connectionSet);
                    break;
                case GetContractCardsList:
                    onGetContractCardsList((GetContractCardsList) event, setup, connectionSet);
                    break;
                case GetContractStatusChangeDatesEvent:
                    onGetContractStatusChangeDatesEvent((GetContractStatusChangeDatesEvent) event, setup, connectionSet);
                    break;
                case GetTariffListEvent:
                    onGetTariffListEvent((GetTariffListEvent) event, setup, connectionSet);
                    break;
                case LimitChangedEvent:
                    onLimitChangedEvent((LimitChangedEvent) event, setup, connectionSet);
                    break;
                case ServerStartEvent:
                    onServerStartEvent((ServerStartEvent) event, setup, connectionSet);
                    break;
                case ServiceUpdateEvent:
                    onServiceUpdateEvent((ServiceUpdateEvent) event, setup, connectionSet);
                    break;
                case TimerEvent:
                    onTimerEvent((TimerEvent) event, setup, connectionSet);
                    break;
                case ValidateTextParamEvent:
                    onValidateTextParamEvent((ValidateTextParamEvent) event, setup, connectionSet);
                    break;

                // ru.bitel.bgbilling.kernel.contract.balance.server.event
                //

                case ChargeEvent:
                    onChargeEvent((ChargeEvent) event, setup, connectionSet);
                    break;

                case PaymentEvent:
                    onPaymentEvent((PaymentEvent) event, setup, connectionSet);
                    break;

                // ru.bitel.bgbilling.modules.rscm.server.event
                //

                case RSCMContractServiceUpdateEvent:
                    onRSCMContractServiceUpdateEvent((RSCMContractServiceUpdateEvent) event, setup, connectionSet);
                    break;

                // ru.bitel.bgbilling.kernel.contract.api.common.event
                //

                case ContractPasswordChangedEvent:
                    onContractPasswordChangedEvent((ContractPasswordChangedEvent) event, setup, connectionSet);
                    break;

                // ru.bitel.bgbilling.kernel.tariff.option.server.event
                //

                case TariffOptionActivatedEvent:
                    onTariffOptionActivatedEvent((TariffOptionActivatedEvent) event, setup, connectionSet);
                    break;

                case TariffOptionBeforeActivateEvent:
                    onTariffOptionBeforeActivateEvent((TariffOptionBeforeActivateEvent) event, setup, connectionSet);
                    break;

                case TariffOptionListAvailableEvent:
                    onTariffOptionListAvailableEvent((TariffOptionListAvailableEvent) event, setup, connectionSet);
                    break;

                // ru.bitel.bgbilling.plugins.helpdesk.server.bean.event
                //

                case TopicWasUpdatedEvent:
                    onTopicWasUpdatedEvent((TopicWasUpdatedEvent) event, setup, connectionSet);
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

    private void onActionAfterEvent(ActionAfterEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onActionBeforeEvent(ActionBeforeEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onAdditionalActionEvent(AdditionalActionEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onBeforeServiceDeleteEvent(BeforeServiceDeleteEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onCalculateEvent(CalculateEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onCancelTariffEvent(CancelTariffEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onChangeContractLimitEvent(ChangeContractLimitEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onChangeTariffByTaskEvent(ChangeTariffByTaskEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractAddingSubEvent(ContractAddingSubEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractAddObjectEvent(ContractAddObjectEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractCreatedEvent(ContractCreatedEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractDeleteObjectEvent(ContractDeleteObjectEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractLimitUserLow(ContractLimitUserLow event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractParamBeforeChangeEvent(ContractParamBeforeChangeEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractParamChangedEvent(ContractParamChangedEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onContractTariffDeleteEvent(ContractTariffDeleteEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString() + "; contractTariffId: " + event.getContractTariffId());
    }

    private void onContractTariffUpdateEvent(ContractTariffUpdateEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString() + "; isAddTariff: " + event.isAddTariff() + "; contractTariffId: " + event.getContractTariff().getId());
    }

    private void onContractWebLoginEvent(ContractWebLoginEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onGetChangeTariffDatesEvent(GetChangeTariffDatesEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onGetContractCardsList(GetContractCardsList event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onGetContractStatusChangeDatesEvent(GetContractStatusChangeDatesEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onGetTariffListEvent(GetTariffListEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onLimitChangedEvent(LimitChangedEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onServerStartEvent(ServerStartEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onServiceUpdateEvent(ServiceUpdateEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onTimerEvent(TimerEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onValidateTextParamEvent(ValidateTextParamEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.kernel.contract.balance.server.event
    //

    private void onChargeEvent(ChargeEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onPaymentEvent(PaymentEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.modules.rscm.server.event
    //

    private void onRSCMContractServiceUpdateEvent(RSCMContractServiceUpdateEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.kernel.contract.api.common.event
    //

    private void onContractPasswordChangedEvent(ContractPasswordChangedEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.kernel.tariff.option.server.event
    //

    private void onTariffOptionActivatedEvent(TariffOptionActivatedEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onTariffOptionBeforeActivateEvent(TariffOptionBeforeActivateEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    private void onTariffOptionListAvailableEvent(TariffOptionListAvailableEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    // ru.bitel.bgbilling.plugins.helpdesk.server.bean.event
    //

    private void onTopicWasUpdatedEvent(TopicWasUpdatedEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().trace("onEvent: " + event.toString());
    }

    enum CLAZZ {
        // ru.bitel.bgbilling.kernel.event.events
        // --------------------------------------
        ActionAfterEvent,
        ActionBeforeEvent,
        // ActionEvent
        AdditionalActionEvent,
        // AppsEvent
        BeforeServiceDeleteEvent,
        CalculateEvent,
        CancelTariffEvent,
        ChangeContractLimitEvent,
        ChangeTariffByTaskEvent,
        ContractAddingSubEvent,
        ContractAddObjectEvent,
        ContractCreatedEvent,
        ContractDeleteObjectEvent,
        ContractLimitUserLow,
        // ContractObjectParameterBeforeUpdateEvent
        // ContractObjectParameterUpdateEvent
        ContractParamBeforeChangeEvent,
        ContractParamChangedEvent,
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
        GetContractStatusChangeDatesEvent,
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
        ChargeEvent,
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
        TariffOptionActivatedEvent,
        TariffOptionBeforeActivateEvent,
        // TariffOptionDeactivatedEvent
        // TariffOptionDeactivateEvent
        TariffOptionListAvailableEvent,

        // ru.bitel.bgbilling.plugins.helpdesk.server.bean.event
        // -----------------------------------------------------
        TopicWasUpdatedEvent
    }

    private static final String LOG_CONTEXT = "murmuring";

}
