import bitel.billing.common.VersionInfo;
import ru.bitel.bgbilling.common.BGException;
import ru.bitel.bgbilling.kernel.admin.plugincfg.common.PluginItem;
import ru.bitel.bgbilling.kernel.admin.plugincfg.common.PlugincfgService;
import ru.bitel.bgbilling.kernel.container.managed.ServerContext;
import ru.bitel.bgbilling.kernel.module.common.bean.BGModule;
import ru.bitel.bgbilling.kernel.module.server.ModuleCache;
import ru.bitel.bgbilling.kernel.script.common.EventScriptService;
import ru.bitel.bgbilling.kernel.script.common.bean.EventScriptLink;
import ru.bitel.bgbilling.kernel.script.server.dev.GlobalScriptBase;
import ru.bitel.bgbilling.kernel.task.common.SchedulerService;
import ru.bitel.bgbilling.kernel.task.common.bean.LightweightTaskData;
import ru.bitel.bgbilling.server.util.Setup;
import ru.bitel.common.sql.ConnectionSet;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;

public class SysInfo extends GlobalScriptBase {

    @Override
    public void execute(Setup setup, ConnectionSet connectionSet) throws Exception {
        System.out.println(new Date().toString() + NL);

        inspectModules();
        inspectPlugins();
        inspectScheduledTasks();
        inspectEventHandlers();
        inspectRuntime();
        inspectSystemProperties();
        inspectEnvironment();
        inspectConnections(connectionSet);
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

    private void inspectPlugins() throws BGException {
        System.out.println(String.join(NL,
                "Plugins",
                HR
        ));
        ServerContext context = ServerContext.get();
        PlugincfgService service = context.getService(PlugincfgService.class, 0);
        List<PluginItem> plugins = service.getPlugins();
        for (PluginItem plugin : plugins) {
            VersionInfo ver = VersionInfo.getVersionInfo(plugin.getName());
            System.out.println(String.join(SPACE,
                    "p" + plugin.getId(),
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

    private void inspectConnections(ConnectionSet connectionSet) throws SQLException {
        System.out.println(String.join(NL,
                "Connections",
                HR
        ));

        System.out.println("Master connection");
        inspectConnection(connectionSet.getConnection());
        System.out.println("Slave connection");
        inspectConnection(connectionSet.getSlaveConnection());
        System.out.println();
    }

    private void inspectConnection(Connection connection) throws SQLException {
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

    private void inspectEventHandlers() throws BGException {
        System.out.println(String.join(NL,
                "Event handlers",
                HR
        ));
        ServerContext context = ServerContext.get();
        EventScriptService service = context.getService(EventScriptService.class, 0);
        List<EventScriptLink> handlers = service.getEventLinks();
        for (EventScriptLink handler : handlers) {
            System.out.println(handler.getEventKey() + ": " + handler.getClassName());
        }
        System.out.println();
    }

    private void inspectScheduledTasks() throws BGException {
        System.out.println(String.join(NL,
                "Scheduled tasks",
                HR
        ));
        inspectScheduledTasks("0");
        List<BGModule> modules = ModuleCache.getInstance().getModulesList();
        for (BGModule module : modules) {
            String moduleId = Integer.toString(module.getId());
            inspectScheduledTasks(moduleId);
        }
        ServerContext context = ServerContext.get();
        PlugincfgService service = context.getService(PlugincfgService.class, 0);
        List<PluginItem> plugins = service.getPlugins();
        for (PluginItem plugin : plugins) {
            inspectScheduledTasks("p" + plugin.getId());
        }
        System.out.println();
    }

    private void inspectScheduledTasks(String moduleId) throws BGException {
        ServerContext context = ServerContext.get();
        SchedulerService service = context.getService(SchedulerService.class, 0);
        List<LightweightTaskData> tasks = service.getSchedulerTasks(moduleId);
        for (LightweightTaskData task : tasks) {
            String data = String.join(" ",
                    task.getStatus() == 0 ? "-" : "+",
                    moduleId,
                    task.getModuleName(),
                    Integer.toString(task.getPriority()),
                    task.getClassName(),
                    "[",
                    Integer.toString(task.getMonth()),
                    Integer.toString(task.getDay()),
                    Integer.toString(task.getDayOfWeek()),
                    Integer.toString(task.getHour()),
                    Long.toString(task.getMin()),
                    "]"
            );
            System.out.println(data);
        }
    }

    private final static String HR = "--------------------------------------------------";
    private final static String NL = "\n";
    private final static String SPACE = " ";
    private final static int MB = 1024 * 1024;

}
