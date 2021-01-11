package vn.tcx.dw.database.utils;

import lombok.extern.slf4j.Slf4j;
import vn.tcx.dw.database.constant.SQLServerConstants;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.DBPDriver;
import vn.tcx.dw.database.model.enums.SQLServerAuthentication;

@Slf4j
public class SQLServerUtils {

    public static boolean isDriverSqlServer(DBPDriver driver) {
        return driver.getSampleURL().contains(":sqlserver");
    }

    public static boolean isDriverGeneric(DBPDriver driver) {
        return driver.getId().contains("generic");
    }

    public static boolean isDriverAzure(DBPDriver driver) {
        return driver.getId().contains("azure");
    }

    public static boolean isDriverJtds(DBPDriver driver) {
        return driver.getSampleURL().startsWith("jdbc:jtds");
    }

    public static SQLServerAuthentication detectAuthSchema(DBPConnectionInfo connectionInfo) {
        // Detect auth schema
        // Now we use only PROP_AUTHENTICATION but here we support all legacy SQL Server
        // configs
        SQLServerAuthentication auth = isWindowsAuth(connectionInfo) ? SQLServerAuthentication.WINDOWS_INTEGRATED
                : (isActiveDirectoryAuth(connectionInfo) ? SQLServerAuthentication.AD_PASSWORD
                        : SQLServerAuthentication.SQL_SERVER_PASSWORD);

        {
            String authProp = connectionInfo.getProviderProperty(SQLServerConstants.PROP_AUTHENTICATION);
            if (authProp != null) {
                try {
                    auth = SQLServerAuthentication.valueOf(authProp);
                } catch (IllegalArgumentException e) {
                    log.warn("Bad auth schema: " + authProp);
                }
            }
        }

        return auth;
    }

    public static boolean isWindowsAuth(DBPConnectionInfo connectionInfo) {
        return CommonUtils
                .toBoolean(connectionInfo.getProviderProperty(SQLServerConstants.PROP_CONNECTION_WINDOWS_AUTH))
                || CommonUtils.toBoolean(
                        connectionInfo.getProperties().get(SQLServerConstants.PROP_CONNECTION_INTEGRATED_SECURITY));
    }

    public static boolean isActiveDirectoryAuth(DBPConnectionInfo connectionInfo) {
        return SQLServerConstants.AUTH_ACTIVE_DIRECTORY_PASSWORD
                .equals(connectionInfo.getProperty(SQLServerConstants.PROP_CONNECTION_AUTHENTICATION));
    }
}
