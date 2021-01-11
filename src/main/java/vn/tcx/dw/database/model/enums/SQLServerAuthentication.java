package vn.tcx.dw.database.model.enums;

import java.util.Properties;

import vn.tcx.dw.database.constant.DBConstants;
import vn.tcx.dw.database.constant.SQLServerConstants;
import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.utils.CommonUtils;
import vn.tcx.dw.database.utils.SQLServerGSS;

public enum SQLServerAuthentication {

    SQL_SERVER_PASSWORD("authentication_sql_server_title", "authentication_sql_server_description", true, true, true,
            (connectionInfo, properties) -> {
                properties.put(SQLServerConstants.PROP_CONNECTION_INTEGRATED_SECURITY, String.valueOf(false));
                // properties.put(SQLServerConstants.PROP_CONNECTION_AUTHENTICATION,
                // SQLServerConstants.AUTH_SQL_SERVER_PASSWORD);

                setStandardCredentials(connectionInfo, properties);
            }),
    WINDOWS_INTEGRATED("authentication_windows_title", "authentication_windows_description", true, false, false,
            (connectionInfo, properties) -> {
                properties.put(SQLServerConstants.PROP_CONNECTION_INTEGRATED_SECURITY, String.valueOf(true));
            }),
    NTLM("NTLM", "NTLM authentication", true, true, true, (connectionInfo, properties) -> {
        properties.put(SQLServerConstants.PROP_CONNECTION_INTEGRATED_SECURITY, String.valueOf(true));
        properties.put(SQLServerConstants.PROP_CONNECTION_AUTHENTICATION_SCHEME, SQLServerConstants.AUTH_NTLM);
        String userName = connectionInfo.getUserName();
        int divPos = userName.indexOf('@');
        if (divPos != -1) {
            properties.put(SQLServerConstants.PROP_DOMAIN, userName.substring(divPos + 1));
            properties.put(DBConstants.DATA_SOURCE_PROPERTY_USER, userName.substring(0, divPos));
        }
    }), AD_PASSWORD("authentication_ad_password_title", "authentication_ad_password_description", false, true, true,
            (connectionInfo, properties) -> {
                properties.put(SQLServerConstants.PROP_CONNECTION_INTEGRATED_SECURITY, String.valueOf(false));
                properties.put(SQLServerConstants.PROP_CONNECTION_AUTHENTICATION,
                        SQLServerConstants.AUTH_ACTIVE_DIRECTORY_PASSWORD);

                if (!CommonUtils.isEmpty(connectionInfo.getUserName())) {
                    properties.put("UserName", connectionInfo.getUserName());
                }
                if (!CommonUtils.isEmpty(connectionInfo.getUserPassword())) {
                    properties.put("Password", connectionInfo.getUserPassword());
                }
            }),
    AD_MSI("authentication_ad_msi_title", "authentication_ad_msi_description", false, true, false,
            (connectionInfo, properties) -> {
                properties.put(SQLServerConstants.PROP_CONNECTION_AUTHENTICATION,
                        SQLServerConstants.AUTH_ACTIVE_DIRECTORY_MSI);
                if (!CommonUtils.isEmpty(connectionInfo.getUserName())) {
                    properties.put("msiClientId", connectionInfo.getUserName());
                    properties.remove(DBConstants.DATA_SOURCE_PROPERTY_USER);
                    properties.remove(DBConstants.DATA_SOURCE_PROPERTY_PASSWORD);
                }
            }),
    AD_INTEGRATED("authentication_ad_integrated_title", "authentication_ad_integrated_description", false, false, false,
            (connectionInfo, properties) -> {
                properties.put(SQLServerConstants.PROP_CONNECTION_AUTHENTICATION,
                        SQLServerConstants.AUTH_ACTIVE_DIRECTORY_INTEGRATED);
                properties.remove(DBConstants.DATA_SOURCE_PROPERTY_USER);
                properties.remove(DBConstants.DATA_SOURCE_PROPERTY_PASSWORD);
            }),
    KERBEROS_INTEGRATED("authentication_kerberos_title", "authentication_kerberos_description", false, false, false,
            (connectionInfo, properties) -> {
                properties.put(SQLServerConstants.PROP_CONNECTION_INTEGRATED_SECURITY, String.valueOf(true));
                properties.put(SQLServerConstants.PROP_CONNECTION_AUTHENTICATION_SCHEME,
                        SQLServerConstants.AUTH_SCHEME_KERBEROS);

                if (SQLServerConstants.USE_GSS) {
                    // Disabled by default. Never really tested
                    SQLServerGSS.initCredentials(connectionInfo, properties);
                }
            }),
    OTHER("authentication_other_title", "authentication_other_description", true, true, true,
            (connectionInfo, properties) -> {
                // Set standard JDBC creds
                setStandardCredentials(connectionInfo, properties);
                // Nothing special
            });

    private static void setStandardCredentials(DBPConnectionInfo connectionInfo, Properties properties) {
        if (!CommonUtils.isEmpty(connectionInfo.getUserName())) {
            properties.put(DBConstants.DATA_SOURCE_PROPERTY_USER, connectionInfo.getUserName());
        }
        if (!CommonUtils.isEmpty(connectionInfo.getUserPassword())) {
            properties.put(DBConstants.DATA_SOURCE_PROPERTY_PASSWORD, connectionInfo.getUserPassword());
        }
    }

    private final String title;
    private final String description;
    private final boolean supportsJTDS;
    private final boolean allowsPassword;
    private final boolean allowsUserName;
    private final AuthInitializer initializer;

    SQLServerAuthentication(String title, String description, boolean supportsJTDS, boolean allowsUserName,
            boolean allowsPassword, AuthInitializer initializer) {
        this.title = title;
        this.description = description;
        this.supportsJTDS = supportsJTDS;
        this.allowsUserName = allowsUserName;
        this.allowsPassword = allowsPassword;
        this.initializer = initializer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSupportsJTDS() {
        return supportsJTDS;
    }

    public boolean isAllowsUserName() {
        return allowsUserName;
    }

    public boolean isAllowsPassword() {
        return allowsPassword;
    }

    public AuthInitializer getInitializer() {
        return initializer;
    }

    public interface AuthInitializer {

        void initializeAuthentication(DBPConnectionInfo connectionInfo, Properties properties) throws DBException;
    }
}
