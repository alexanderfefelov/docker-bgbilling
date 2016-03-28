package com.github.alexanderfefelov.bgbilling.device.murmuring;

import org.apache.log4j.*;
import ru.bitel.common.*;
import ru.bitel.common.sql.*;
import ru.bitel.bgbilling.server.util.*;
import ru.bitel.bgbilling.modules.inet.api.common.bean.*;
import ru.bitel.bgbilling.modules.inet.access.sa.*;
import ru.bitel.bgbilling.kernel.network.dhcp.*;
import ru.bitel.bgbilling.kernel.network.radius.*;

public class MurmuringProtocolHandler implements ProtocolHandler {

    @Override
    public void init(Setup setup, int moduleId, InetDevice device, InetDeviceType deviceType, ParameterMap config) {
        logger.trace("init: " + device.toString());

        this.device = device;
        this.config = config;
    }

    @Override
    public void preprocessDhcpRequest(DhcpPacket request, DhcpPacket response) {
        logger.trace("preprocessDhcpRequest: " + device.toString() + ", " + request.toString());
    }

    @Override
    public void postprocessDhcpRequest(DhcpPacket request, DhcpPacket response) {
        logger.trace("postprocessDhcpRequest: " + device.toString() + ", " + response.toString());
    }

    @Override
    public void preprocessAccessRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger.trace("preprocessAccessRequest: " + device.toString() + ", " + request.toString());
    }

    @Override
    public void postprocessAccessRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger.trace("postprocessAccessRequest: " + device.toString() + ", " + response.toString());
    }

    @Override
    public void preprocessAccountingRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger.trace("preprocessAccountingRequest: " + device.toString() + ", " + request.toString());
    }

    @Override
    public void postprocessAccountingRequest(RadiusPacket request, RadiusPacket response, ConnectionSet connectionSet) {
        logger.trace("postprocessAccountingRequest: " + device.toString() + ", " + response.toString());
    }

    private InetDevice device;
    private ParameterMap config;

    private static final Logger logger = Logger.getLogger(MurmuringProtocolHandler.class);

}
