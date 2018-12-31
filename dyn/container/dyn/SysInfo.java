import bitel.billing.common.VersionInfo;
import ru.bitel.bgbilling.kernel.module.common.bean.BGModule;
import ru.bitel.bgbilling.kernel.module.server.ModuleCache;
import ru.bitel.bgbilling.kernel.script.server.dev.GlobalScriptBase;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.*;

public class SysInfo extends GlobalScriptBase {

    @Override
    public void execute(Setup setup, ConnectionSet connectionSet) throws Exception {
        System.out.println(new Date().toString() + NL);

        inspectModules();
        inspectRuntime();
        inspectSystemProperties();
        inspectEnvironment();
        inspectConnectionSet(connectionSet);
    }

    private void inspectModules() {
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

    private void inspectRuntime() throws Exception {
        InetAddress ipAddress = InetAddress.getLocalHost();
        System.out.println(String.join(NL,
                "Runtime",
                HR,
                "Hostname/IP address: " + ipAddress,
                "Available processors: " + Runtime.getRuntime().availableProcessors(),
                "Memory free / max / total, MB: "
                        + Runtime.getRuntime().freeMemory() / MB + " / "
                        + Runtime.getRuntime().maxMemory() / MB + " / "
                        + Runtime.getRuntime().totalMemory() / MB
        ));
        System.out.println();
    }

    private void inspectSystemProperties() {
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

    private void inspectEnvironment() {
        System.out.println(String.join(NL,
                "Environment",
                HR
        ));
        Map<String, String> env = System.getenv();
        List<String> keys = new ArrayList<>(env.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            System.out.println(key + "=" + env.get(key));
        }
        System.out.println();
    }

    private void inspectConnectionSet(ConnectionSet connectionSet) throws Exception  {
        System.out.println(String.join(NL,
                "ConnectionSet",
                HR
        ));

        System.out.println("Master connection");
        inspectConnection(connectionSet.getConnection());
        System.out.println("Slave connection");
        inspectConnection(connectionSet.getSlaveConnection());
        System.out.println();
    }

    private void inspectConnection(Connection connection) throws Exception {
        if (connection == null) {
            System.out.println("\tNo connection found");
            return;
        }

        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println("\tURL: " + metaData.getURL());
        System.out.println("\tUsername: " + metaData.getUserName());
        System.out.println("\tProduct: " + metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion());
        System.out.println("\tDriver: " + metaData.getDriverName() + " " + metaData.getDriverVersion());
    }

    private final static String HR = "--------------------------------------------------";
    private final static String NL = "\n";
    private final static String SPACE = " ";
    private final static int MB = 1024 * 1024;

}
