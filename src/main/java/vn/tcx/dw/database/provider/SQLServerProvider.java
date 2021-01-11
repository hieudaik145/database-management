package vn.tcx.dw.database.provider;

import java.util.HashMap;
import java.util.Map;

import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.DBPDriver;
import vn.tcx.dw.database.utils.CommonUtils;
import vn.tcx.dw.database.utils.SQLServerUtils;

public class SQLServerProvider {

    private static Map<String, String> connectionsProps;

    static {
        connectionsProps = new HashMap<>();
    }

    public static Map<String, String> getConnectionProps() {
        return connectionsProps;
    }

    public static String getConnectionStringURL(DBPConnectionInfo connectionInfo) {
        StringBuilder url = new StringBuilder();
        DBPDriver driver = connectionInfo.getDriver();
        boolean isJtds = SQLServerUtils.isDriverJtds(driver);
        boolean isSqlServer = SQLServerUtils.isDriverSqlServer(driver);
        boolean isDriverAzure = isSqlServer && SQLServerUtils.isDriverAzure(driver);

        if (isSqlServer) {
            // SQL Server
            if (isJtds) {
                url.append("jdbc:jtds:sqlserver://");
                url.append(connectionInfo.getHostName());
                if (!CommonUtils.isEmpty(connectionInfo.getHostPort())
                        && !connectionInfo.getHostPort().equals(driver.getDefaultPort())) {
                    url.append(":").append(connectionInfo.getHostPort());
                }
            } else {
                url.append("jdbc:sqlserver://");
                url.append(";serverName=").append(connectionInfo.getHostName());
                if (!CommonUtils.isEmpty(connectionInfo.getHostPort())
                        && !connectionInfo.getHostPort().equals(driver.getDefaultPort())) {
                    url.append(";port=").append(connectionInfo.getHostPort());
                }
            }
            if (isJtds) {
                if (!CommonUtils.isEmpty(connectionInfo.getDatabaseName())) {
                    url.append("/").append(connectionInfo.getDatabaseName());
                }
            } else {
                url.append(";");
                if (!CommonUtils.isEmpty(connectionInfo.getDatabaseName())) {
                    url.append("databaseName=").append(connectionInfo.getDatabaseName());
                }

                if (isDriverAzure) {
                    url.append(";encrypt=true"); // ;hostNameInCertificate=*.database.windows.net
                }
            }
        } else {
            // Sybase
            if (isJtds) {
                url.append("jdbc:jtds:sybase://");
                url.append(connectionInfo.getHostName());
                if (!CommonUtils.isEmpty(connectionInfo.getHostPort())) {
                    url.append(":").append(connectionInfo.getHostPort());
                }
                if (!CommonUtils.isEmpty(connectionInfo.getDatabaseName())) {
                    url.append("/").append(connectionInfo.getDatabaseName());
                }
            } else {
                url.append("jdbc:sybase:Tds:");
                url.append(connectionInfo.getHostName());
                if (!CommonUtils.isEmpty(connectionInfo.getHostPort())) {
                    url.append(":").append(connectionInfo.getHostPort());
                }
                if (!CommonUtils.isEmpty(connectionInfo.getDatabaseName())) {
                    url.append("?ServiceName=").append(connectionInfo.getDatabaseName());
                }
            }
        }
        return url.toString();
    }

}
