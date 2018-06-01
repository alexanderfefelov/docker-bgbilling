package com.github.alexanderfefelov.bgbilling.dyn.device.murmuring;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import com.github.alexanderfefelov.bgbilling.dyn.framework.Utils;
import ru.bitel.bgbilling.kernel.network.dhcp.DhcpPacket;
import ru.bitel.bgbilling.kernel.network.radius.RadiusPacket;
import ru.bitel.bgbilling.modules.inet.access.sa.ProtocolHandler;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDevice;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDeviceType;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.ParameterMap;
import ru.bitel.common.sql.ConnectionSet;

public class MurmuringProtocolHandler implements ProtocolHandler,
        Loggable, Utils {

    @Override
    public void init(Setup setup, int moduleId, InetDevice device, InetDeviceType deviceType, ParameterMap config) {
        logger().trace("init: [" + device.getId() + "] " + device.toString() + " " + removeNewLines(config.toString()));

        this.device = device;
        this.config = config;
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

    private InetDevice device;
    private ParameterMap config;

}
