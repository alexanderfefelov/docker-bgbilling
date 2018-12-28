package com.github.alexanderfefelov.bgbilling.dyn.inet.device.murmuring;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import com.github.alexanderfefelov.bgbilling.dyn.framework.Utils;
import ru.bitel.bgbilling.modules.inet.access.sa.ServiceActivator;
import ru.bitel.bgbilling.modules.inet.access.sa.ServiceActivatorEvent;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDevice;
import ru.bitel.bgbilling.modules.inet.api.common.bean.InetDeviceType;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.ParameterMap;
import ru.bitel.common.logging.NestedContext;

public class MurmuringServiceActivator implements ServiceActivator, Loggable, Utils {

    @Override
    public Object init(Setup setup, int moduleId, InetDevice device, InetDeviceType deviceType, ParameterMap config) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("init: [" + device.getId() + "] " + device.toString() + " " + removeNewLines(config.toString()));

            this.device = device;
            this.config = config;
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object destroy() {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("destroy: [" + device.getId() + "] " + device.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object connect() {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("connect: [" + device.getId() + "] " + device.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object disconnect() {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("disconnect: [" + device.getId() + "] " + device.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object serviceCreate(ServiceActivatorEvent event) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("serviceCreate: [" + device.getId() + "] " + device.toString() + ", " + event.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object serviceModify(ServiceActivatorEvent event) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("serviceModify: [" + device.getId() + "] " + device.toString() + ", " + event.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object serviceCancel(ServiceActivatorEvent event) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("serviceCancel: [" + device.getId() + "] " + device.toString() + ", " + event.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object connectionModify(ServiceActivatorEvent event) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("connectionModify: [" + device.getId() + "] " + device.toString() + ", " + event.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object connectionClose(ServiceActivatorEvent event) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("connectionClose: [" + device.getId() + "] " + device.toString() + ", " + event.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object onAccountingStart(ServiceActivatorEvent event) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("onAccountingStart: [" + device.getId() + "] " + device.toString() + ", " + event.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    @Override
    public Object onAccountingStop(ServiceActivatorEvent event) {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("onAccountingStop: [" + device.getId() + "] " + device.toString() + ", " + event.toString());
        } finally {
            NestedContext.pop();
        }

        return true;
    }

    private InetDevice device;
    private ParameterMap config;

    private static final String LOG_CONTEXT = "murmuring";

}
