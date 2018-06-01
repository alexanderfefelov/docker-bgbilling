package com.github.alexanderfefelov.bgbilling.dyn.graalvm;

import com.github.alexanderfefelov.bgbilling.dyn.framework.Loggable;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class PolyglotRunner
        implements Loggable {

    public Value run(PolyglotLanguage language, String relativeScriptPath, long timeout,
                     boolean allowCreateThread, boolean allowHostAccess, boolean allowIO, boolean allowNativeAccess) {
        logger().trace(String.format("run: %s, %s, %d, %b, %b, %b, %b", language.language(), relativeScriptPath, timeout, allowCreateThread, allowHostAccess, allowIO, allowNativeAccess));
        Context context = Context
                .newBuilder()
                .allowCreateThread(allowCreateThread)
                .allowHostAccess(allowHostAccess)
                .allowIO(allowIO)
                .allowNativeAccess(allowNativeAccess)
                .build();
        File sourceFile = new File(SCRIPT_PATH_PREFIX + relativeScriptPath);

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.close(true);
            }
        }, timeout);

        try {
            Source source = Source.newBuilder(language.language(), sourceFile).build();
            Value result = context.eval(source);
            logger().trace(String.format("result: %s", result.toString()));
            return result;
        } catch (Exception e) {
            logger().error(e.toString());
            return null;
        }
    }

    private static final String SCRIPT_PATH_PREFIX = "/bgbilling/polyglot/";

}
