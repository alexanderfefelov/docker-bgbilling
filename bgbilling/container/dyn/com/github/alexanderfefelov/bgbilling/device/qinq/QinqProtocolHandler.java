package com.github.alexanderfefelov.bgbilling.device.qinq;

import com.github.alexanderfefelov.bgbilling.framework.Loggable;
import com.github.alexanderfefelov.bgbilling.framework.Utils;
import ru.bitel.bgbilling.kernel.network.dhcp.DhcpPacket;
import ru.bitel.bgbilling.kernel.network.radius.RadiusPacket;
import ru.bitel.bgbilling.modules.inet.access.sa.ProtocolHandler;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDevice;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDeviceType;
import ru.bitel.bgbilling.modules.inet.dhcp.InetDhcpProcessor;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.ParameterMap;
import ru.bitel.common.sql.ConnectionSet;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QinqProtocolHandler implements ProtocolHandler,
        Loggable, Utils {

    @Override
    public void init(Setup setup, int moduleId, InetDevice device, InetDeviceType deviceType, ParameterMap config) {
        logger().trace("init: [" + device.getId() + "] " + device.toString() + " " + removeNewLines(config.toString()));

        this.device = device;
        this.config = config;
        vlansRegexPattern = Pattern.compile(config.get("qinq.vlansRegex", ".*s(\\d\\d\\d\\d)c(\\d\\d\\d\\d).*"));
    }

    @Override
    public void preprocessDhcpRequest(DhcpPacket request, DhcpPacket response) {
        String option82Str = request.getOption((byte)82).getValueAsString();
        Matcher matcher = vlansRegexPattern.matcher(option82Str);
        if (!matcher.find()) {
            // TODO
            return;
        }
        byte[] agentCircuitId = matcher.group(1).getBytes();
        byte[] vlanId = ByteBuffer.allocate(2).putShort(Short.parseShort(matcher.group(2))).array();
        request.setOption(InetDhcpProcessor.AGENT_CIRCUIT_ID, agentCircuitId);
        request.setOption(InetDhcpProcessor.VLAN_ID, vlanId);
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
    private Pattern vlansRegexPattern;

}
