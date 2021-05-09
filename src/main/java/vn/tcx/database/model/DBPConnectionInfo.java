package vn.tcx.database.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import vn.tcx.database.model.enums.DBAuthType;
import vn.tcx.database.model.enums.DBType;

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
    private DBWHandlerConfiguration ssl;
    private Map<String, String> properties = new HashMap<>();
    private Map<String, String> providerProperties = new HashMap<>();
    private DBAuthType authType;

}
