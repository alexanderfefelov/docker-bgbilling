package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.payment;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import org.json.JSONObject;
import ru.bitel.bgbilling.kernel.contract.balance.common.bean.Payment;
import ru.bitel.bgbilling.kernel.contract.balance.server.event.PaymentEvent;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

public class EnronPaymentHandler extends EventScriptBase<PaymentEvent> implements Loggable {

    @Override
    public void onEvent(PaymentEvent event, Setup setup, ConnectionSet connectionSet) throws Exception {
        logger().info("onEvent: contractId: " + event.getContractId());
        try {
            if (!event.isEditMode()) {
                Payment payment = event.getPayment();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("timestamp", event.getTimestamp());
                jsonObject.put("id", payment.getId());
                jsonObject.put("typeId", payment.getTypeId());
                jsonObject.put("userId", payment.getUserId());
                jsonObject.put("contractId", payment.getContractId());
                jsonObject.put("date", payment.getDate());
                jsonObject.put("description", payment.getComment());
                jsonObject.put("amount", payment.getSum());
                logger().info("Handling payment: " + jsonObject);
            }
        } catch (Exception e) {
            logger().error(e.getMessage());
        }
    }

}
