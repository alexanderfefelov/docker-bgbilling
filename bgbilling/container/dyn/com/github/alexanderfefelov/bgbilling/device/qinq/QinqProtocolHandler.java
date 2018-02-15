package com.github.alexanderfefelov.bgbilling.device.qinq;

import com.github.alexanderfefelov.bgbilling.framework.Loggable;
import com.github.alexanderfefelov.bgbilling.framework.Utils;
import ru.bitel.bgbilling.kernel.network.dhcp.DhcpPacket;
import ru.bitel.bgbilling.kernel.network.radius.RadiusPacket;
import ru.bitel.bgbilling.modules.inet.access.sa.ProtocolHandler;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDevice;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDeviceType;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.ParameterMap;
import ru.bitel.common.sql.ConnectionSet;

import java.io.StringReader;
import java.util.*;

public class QinqProtocolHandler implements ProtocolHandler,
        Loggable, Utils {

    @Override
    public void init(Setup setup, int moduleId, InetDevice device, InetDeviceType deviceType, ParameterMap config) {
        logger().trace("init: [" + device.getId() + "] " + device.toString() + " " + removeNewLines(config.toString()));

        this.device = device;
        this.config = config;
        qinqMap = createQinqMap(device);

        logger().trace("init: [" + device.getId() + "] " + Arrays.toString(qinqMap.entrySet().toArray()));
    }

    @Override
    public void preprocessDhcpRequest(DhcpPacket request, DhcpPacket response) {
        logger().trace("preprocessDhcpRequest: [" + device.getId() + "] " + device.toString() + ", " + removeNewLines(request.toString()));
    }

    @Override
    public void postprocessDhcpRequest(DhcpPacket request, DhcpPacket response) {
        logger().trace("postprocessDhcpRequest: [" + device.getId() + "] " + device.toString() + ", " + removeNewLines(response.toString()));
    }

    @Override
    public void preprocessAccessRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("preprocessAccessRequest: [" + device.getId() + "] " + device.toString() + ", " + removeNewLines(request.toString()));
    }

    @Override
    public void postprocessAccessRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("postprocessAccessRequest: [" + device.getId() + "] " + device.toString() + ", " + removeNewLines(response.toString()));
    }

    @Override
    public void preprocessAccountingRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("preprocessAccountingRequest: [" + device.getId() + "] " + device.toString() + ", " + removeNewLines(request.toString()));
    }

    @Override
    public void postprocessAccountingRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("postprocessAccountingRequest: [" + device.getId() + "] " + device.toString() + ", " + removeNewLines(response.toString()));
    }

    private Map<String, List<String>> createQinqMap(InetDevice root) {
        Map<String, List<String>> map = new HashMap<>();
        for (InetDevice container : root.getChildren()) {
            try {
                Properties config = new Properties();
                config.load(new StringReader(container.getConfig()));
                String spvid = config.getProperty("qinq.spvid");
                if (spvid == null) {
                    continue;
                }
                if (!map.containsKey(spvid)) {
                    map.put(spvid, new ArrayList<String>());
                }
                f(container, spvid, map);
            } catch (Throwable t) {
                logger().error("foobar", t);
            }
        }
        return map;
    }

    private void f(InetDevice device, String spvid, Map<String, List<String>> map) {
            List<String> branch = map.get(spvid);
            branch.add(device.getIdentifier());
            for (InetDevice child : device.getChildren()) {
                f(child, spvid, map);
            }
    }

    private InetDevice device;
    private ParameterMap config;
    private Map<String, List<String>> qinqMap;

}
