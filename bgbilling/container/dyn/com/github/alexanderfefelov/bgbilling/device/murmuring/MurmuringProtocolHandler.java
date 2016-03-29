package com.github.alexanderfefelov.bgbilling.device.murmuring;

import com.github.alexanderfefelov.bgbilling.device.framework.Loggable;
import ru.bitel.bgbilling.kernel.network.dhcp.DhcpPacket;
import ru.bitel.bgbilling.kernel.network.radius.RadiusPacket;
import ru.bitel.bgbilling.modules.inet.access.sa.ProtocolHandler;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDevice;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDeviceType;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.ParameterMap;
import ru.bitel.common.sql.ConnectionSet;

public class MurmuringProtocolHandler implements ProtocolHandler,
        Loggable {

    @Override
    public void init(Setup setup, int moduleId, InetDevice device, InetDeviceType deviceType, ParameterMap config) {
        logger().trace("init: " + device.toString());

        this.device = device;
        this.config = config;
    }

    @Override
    public void preprocessDhcpRequest(DhcpPacket request, DhcpPacket response) {
        logger().trace("preprocessDhcpRequest: " + device.toString() + ", " + request.toString());
    }

    @Override
    public void postprocessDhcpRequest(DhcpPacket request, DhcpPacket response) {
        logger().trace("postprocessDhcpRequest: " + device.toString() + ", " + response.toString());
    }

    @Override
    public void preprocessAccessRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("preprocessAccessRequest: " + device.toString() + ", " + request.toString());
    }

    @Override
    public void postprocessAccessRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("postprocessAccessRequest: " + device.toString() + ", " + response.toString());
    }

    @Override
    public void preprocessAccountingRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("preprocessAccountingRequest: " + device.toString() + ", " + request.toString());
    }

    @Override
    public void postprocessAccountingRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger().trace("postprocessAccountingRequest: " + device.toString() + ", " + response.toString());
    }

    private InetDevice device;
    private ParameterMap config;

}
