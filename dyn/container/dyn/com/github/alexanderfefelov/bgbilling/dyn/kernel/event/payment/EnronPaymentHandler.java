package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.payment;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;
import ru.bitel.bgbilling.kernel.contract.balance.common.bean.Payment;
import ru.bitel.bgbilling.kernel.contract.balance.server.event.PaymentEvent;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

import javax.jms.*;

public class EnronPaymentHandler extends EventScriptBase<PaymentEvent> implements Loggable {

    @Override
    public void onEvent(PaymentEvent event, Setup setup, ConnectionSet connectionSet) {
        logger().info("onEvent: contractId: " + event.getContractId());
        try {
            if (!event.isEditMode()) {
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(MQ_USERNAME, MQ_PASSWORD, MQ_URL);
                Connection connection = connectionFactory.createConnection();
                connection.start();
                logger().trace("MQ connection started");
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                logger().trace("MQ session created");
                Destination destination = session.createQueue(MQ_QUEUE_NAME);
                logger().trace("MQ queue created");
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                logger().trace("MQ producer is ready");

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

                logger().info("Processing payment: " + jsonObject);
                TextMessage message = session.createTextMessage(jsonObject.toString());
                producer.send(message);
                logger().info("Payment processed");

                session.close();
                logger().trace("MQ session closed");
                connection.close();
                logger().trace("MQ connection closed");
            }
        } catch (JMSException e) {
            logger().error(e.getMessage());
        }
    }

    private static final String MQ_USERNAME = "bill";
    private static final String MQ_PASSWORD = "bgbilling";
    private static final String MQ_URL = "failover:(tcp://activemq.bgbilling.local:61616)";
    private static final String MQ_QUEUE_NAME = "bgbilling.enron.payment.created";

}
