package com.github.alexanderfefelov.bgbilling.dyn.kernel.event.payment;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.json.JSONObject;
import ru.bitel.bgbilling.kernel.contract.balance.common.bean.Payment;
import ru.bitel.bgbilling.kernel.contract.balance.server.event.PaymentEvent;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

public class EnronPaymentHandler extends EventScriptBase<PaymentEvent> implements Loggable {

    public EnronPaymentHandler() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();
        httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

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

                HttpPost request = new HttpPost("http://enron.bgbilling.local:9000/handle-payment");
                request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
                request.setEntity(new StringEntity(jsonObject.toString()));
                HttpResponse response = httpClient.execute(request);
                logger().info("Received response: " + response.getStatusLine());
            }
        } catch (Exception e) {
            logger().error(e.getMessage());
        }
    }

    private CloseableHttpClient httpClient;

    private static final int MAX_TOTAL_CONNECTIONS = 150;

    // Time to establish the connection with the remote host
    private static final int CONNECT_TIMEOUT = 3000;
    // Time waiting for data after the connection was established
    private static final int SOCKET_TIMEOUT = 3000;
    // Time to wait for a connection from the connection manager/pool
    private static final int CONNECTION_REQUEST_TIMEOUT = 3000;

}
