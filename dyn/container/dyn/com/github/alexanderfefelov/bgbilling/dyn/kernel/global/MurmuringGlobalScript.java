package com.github.alexanderfefelov.bgbilling.dyn.kernel.global;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import ru.bitel.bgbilling.kernel.script.server.dev.GlobalScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.logging.NestedContext;
import ru.bitel.common.sql.ConnectionSet;

public class MurmuringGlobalScript extends GlobalScriptBase implements Loggable {

    @Override
    public void execute(Setup setup, ConnectionSet connectionSet) throws Exception {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger().trace("execute");
        } finally {
            NestedContext.pop();
        }
    }

    private static final String LOG_CONTEXT = "murmuring";

}
