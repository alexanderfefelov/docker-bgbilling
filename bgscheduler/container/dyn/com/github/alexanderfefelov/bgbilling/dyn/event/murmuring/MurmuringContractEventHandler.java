package com.github.alexanderfefelov.bgbilling.dyn.event.murmuring;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.kernel.event.Event;
import ru.bitel.bgbilling.kernel.script.server.dev.EventScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

public class MurmuringContractEventHandler extends EventScriptBase
        implements Loggable {

    @Override
    public void onEvent(Event event, Setup setup, ConnectionSet set) throws Exception {
        logger().trace("onEvent: " + event.toString());
    }

}