package vn.tcx.dw.database.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import vn.tcx.dw.database.auth.AuthModelDatabaseNative;
import vn.tcx.dw.database.auth.DBAAuthModel;
import vn.tcx.dw.database.auth.OracleAuthOS;
import vn.tcx.dw.database.model.enums.DBAuthType;
import vn.tcx.dw.database.model.enums.DBType;

@Data
public class DBPConnectionInfo {

    private String hostName;
    private String hostPort;
    private String serverName;
    private String databaseName;
    private String userName;
    private String userPassword;
    private String url;
    private DBType type;
    private DBPDriver driver;
    private DBPSSL ssl;
    private Map<String, String> properties = new HashMap<>();
    private Map<String, String> providerProperties = new HashMap<>();
    private DBAuthType authType;

    public String getProviderProperty(String name) {
        return providerProperties.get(name);
    }

    public void setProviderProperty(String name, String value) {
        providerProperties.put(name, value);
    }

    public String getProperty(String name) {
        return properties.get(name);
    }

    public DBAAuthModel<?> getAuthMode() {
        
        if (authType == DBAuthType.OS_AUTHENTICATION) {
            return OracleAuthOS.INSTANCE;
        }
        
        return AuthModelDatabaseNative.INSTANCE;
    }

    public void setProperty(String name, String value) {
        properties.put(name, value);
    }
}
