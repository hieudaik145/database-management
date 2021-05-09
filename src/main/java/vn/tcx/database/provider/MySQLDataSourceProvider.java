package vn.tcx.database.provider;

import java.util.HashMap;
import java.util.Map;

import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.enums.DBType;
import vn.tcx.database.utils.CommonUtils;
import vn.tcx.database.utils.GeneralUtils;

public class MySQLDataSourceProvider {

    private static Map<String, String> connectionsProps;

    static {

        connectionsProps = new HashMap<>();

        connectionsProps.put("characterEncoding", GeneralUtils.UTF8_ENCODING);
        connectionsProps.put("tinyInt1isBit", "false");
        connectionsProps.put("interactiveClient", "true");
    }

    public static Map<String, String> getConnectionProps() {
        return connectionsProps;
    }

    public static String getdConnectionStringURL(DBPConnectionInfo connectionInfo) {
        StringBuilder url = new StringBuilder();

        if (connectionInfo.getType() == DBType.MARIADB) {
            url.append("jdbc:mariadb://");
        } else {
            url.append("jdbc:mysql://");
        }
        url.append(connectionInfo.getHostName());
        if (!CommonUtils.isEmpty(connectionInfo.getHostPort())) {
            url.append(":").append(connectionInfo.getHostPort());
        }
        url.append("/");
        if (!CommonUtils.isEmpty(connectionInfo.getDatabaseName())) {
            url.append(connectionInfo.getDatabaseName());
        }
        return url.toString();
    }

}
