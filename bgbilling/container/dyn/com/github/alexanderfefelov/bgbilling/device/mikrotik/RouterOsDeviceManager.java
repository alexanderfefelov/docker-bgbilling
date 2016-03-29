package com.github.alexanderfefelov.bgbilling.device.mikrotik;

import com.github.alexanderfefelov.bgbilling.device.framework.Loggable;
import me.legrange.mikrotik.ApiConnection;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.ParameterMap;
import ru.bitel.oss.systems.inventory.resource.common.bean.Device;
import ru.bitel.oss.systems.inventory.resource.common.bean.DeviceType;
import ru.bitel.oss.systems.inventory.resource.server.DeviceManager;
import ru.bitel.oss.systems.inventory.resource.server.DeviceManagerMethod;

import javax.net.SocketFactory;
import java.util.List;
import java.util.Map;

public class RouterOsDeviceManager implements DeviceManager,
        Loggable {

    @Override
    public Object init(Setup setup, int i, Device<?, ?> device, DeviceType deviceType, ParameterMap config) throws Exception {
        logger().trace("init: " + device.toString());

        this.device = device;
        identifier = device.getIdentifier();
        String[] hostPort = device.getHost().split(":");
        if (hostPort.length < 1 || hostPort.length > 2) {
            throw new IllegalArgumentException("Value " + device.getHost() + " is invalid");
        }
        host = hostPort[0];
        if (hostPort.length == 2) {
            port = Integer.parseInt(hostPort[1]);
        } else {
            port = 8728;
        }

        username = device.getUsername();
        password = device.getPassword();

        formatter = new PeriodFormatterBuilder()
                .appendYears().appendSuffix("y")
                .appendWeeks().appendSuffix("w")
                .appendDays().appendSuffix("d")
                .appendHours().appendSuffix("h")
                .appendMinutes().appendSuffix("m")
                .appendSeconds().appendSuffix("s")
                .toFormatter();

        return true;
    }

    @Override
    public Object destroy() throws Exception {
        logger().trace("destroy: " + device.toString());

        return true;
    }

    @Override
    public Object connect() throws Exception {
        logger().trace("connect: " + device.toString());

        apiConnection = ApiConnection.connect(SocketFactory.getDefault(), host, port, 10 * 1000);
        apiConnection.login(username, password);

        return true;
    }

    @Override
    public Object disconnect() throws Exception {
        logger().trace("disconnect: " + device.toString());

        apiConnection.close();

        return true;
    }

    @Override
    public Object uptime() throws Exception {
        logger().trace("uptime: " + device.toString());

        List<Map<String, String>> results = apiConnection.execute("/system/resource/print");
        String str = results.get(0).get("uptime");
        Period period = formatter.parsePeriod(str);
        long uptime = period.toStandardDuration().getMillis();

        return uptime;
    }

    @DeviceManagerMethod(title = "Перезагрузить")
    public Object reboot() throws Exception {
        logger().trace("reboot: " + device.toString());

        apiConnection.execute("/system/reboot");

        return "Команда на перезагрузку отправлена на " + identifier;
    }

    private Device device;
    private String identifier;
    private String host;
    private int port;
    private String username;
    private String password;
    private ApiConnection apiConnection;
    private PeriodFormatter formatter;

}
