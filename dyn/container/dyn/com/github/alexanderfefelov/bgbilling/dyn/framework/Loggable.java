package com.github.alexanderfefelov.bgbilling.dyn.framework;

import org.apache.log4j.Logger;

public interface Loggable {

    default Logger logger() {
        return Logger.getLogger(this.getClass());
    }

}
