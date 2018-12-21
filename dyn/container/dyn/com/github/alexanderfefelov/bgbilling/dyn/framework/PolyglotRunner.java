package com.github.alexanderfefelov.bgbilling.dyn.framework;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class PolyglotRunner implements Loggable {

    public Value runFile(PolyglotLanguage language, String filePath, long timeout,
                     boolean allowCreateThread, boolean allowHostAccess, boolean allowIO, boolean allowNativeAccess) {
        int id = hashCode();
        logger().trace(String.format("%d running file %s: %s, %d, %b, %b, %b, %b", id, language.language(), filePath, timeout, allowCreateThread, allowHostAccess, allowIO, allowNativeAccess));
        File sourceFile = new File(POLYGLOT_SCRIPT_PATH_PREFIX + filePath);
        try {
            Source source = Source.newBuilder(language.language(), sourceFile).build();
            Value result = run(source, timeout, allowCreateThread, allowHostAccess, allowIO, allowNativeAccess);
            logger().trace(String.format("%d finished", id));
            return result;
        } catch (Exception e) {
            logger().error(e.toString());
            return null;
        }
    }

    public Value runLiteralText(PolyglotLanguage language, String text, long timeout,
                     boolean allowCreateThread, boolean allowHostAccess, boolean allowIO, boolean allowNativeAccess) {
        int id = hashCode();
        logger().trace(String.format("%d running text %s: %s, %d, %b, %b, %b, %b", id, language.language(), text.substring(0, 20), timeout, allowCreateThread, allowHostAccess, allowIO, allowNativeAccess));
        try {
            Source source = Source.newBuilder(language.language(), text, "<literal>").build();
            Value result = run(source, timeout, allowCreateThread, allowHostAccess, allowIO, allowNativeAccess);
            logger().trace(String.format("%d finished", id));
            return result;
        } catch (Exception e) {
            logger().error(e.toString());
            return null;
        }
    }

    private Value run(Source source, long timeout,
                     boolean allowCreateThread, boolean allowHostAccess, boolean allowIO, boolean allowNativeAccess)
            throws PolyglotException {
        Context context = Context
                .newBuilder()
                .allowCreateThread(allowCreateThread)
                .allowHostAccess(allowHostAccess)
                .allowIO(allowIO)
                .allowNativeAccess(allowNativeAccess)
                .build();

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.close(true);
            }
        }, timeout);

        return context.eval(source);
    }

    private static final String POLYGLOT_SCRIPT_PATH_PREFIX = "/bgbilling/polyglot/";

}
