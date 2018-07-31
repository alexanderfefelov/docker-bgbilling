import java.util.*;
import bitel.billing.common.VersionInfo;
import ru.bitel.bgbilling.kernel.module.common.bean.BGModule;
import ru.bitel.bgbilling.kernel.module.server.ModuleCache;

public class SysInfo {

    public static void main(String[] args) {
        System.out.println(new Date().toString() + NL);

        modules();
        runtime();
        systemProperties();
        environment();
    }

    private static void modules() {
        System.out.println(String.join(NL,
            "Modules",
            HR
        ));
        VersionInfo kernelVer = VersionInfo.getVersionInfo("server");
        System.out.println(String.join(SPACE,
            "0",
            kernelVer.getModuleName(),
            kernelVer.getVersionString()
        ));
        List<BGModule> modules = ModuleCache.getInstance().getModulesList();
        for (BGModule module : modules) {
            VersionInfo ver = VersionInfo.getVersionInfo(module.getName());
            System.out.println(String.join(SPACE,
                String.valueOf(module.getId()),
                ver.getModuleName(),
                ver.getVersionString()
            ));
        }
        System.out.println();
    }

    private static void runtime() {
        System.out.println(String.join(NL,
            "Runtime",
            HR,
            "Available processors: " + Runtime.getRuntime().availableProcessors(),
            "Memory free / max / total, MB: "
                + Runtime.getRuntime().freeMemory()/MB + " / "
                + Runtime.getRuntime().maxMemory()/MB + " / "
                + Runtime.getRuntime().totalMemory()/MB,
            NL
        ));
    }

    private static void systemProperties() {
        System.out.println(String.join(NL,
            "System properties",
            HR
        ));
        Map<String, String> properties = new TreeMap<>();
        System.getProperties().forEach((k, v) -> properties.put((String) k, (String) v));
        for (String key : properties.keySet()) {
            System.out.println(key + ": " + properties.get(key));
        }
        System.out.println();
    }

    private static void environment() {
        System.out.println(String.join(NL,
            "Environment",
            HR
        ));
        Map<String, String> env = System.getenv();
        for (String key : env.keySet()) {
            System.out.println(key + "=" + env.get(key));
        }
        System.out.println();
    }

    private final static String HR = "--------------------------------------------------";
    private final static String NL = "\n";
    private final static String SPACE = " ";
    private final static int MB = 1024 * 1024;

}
