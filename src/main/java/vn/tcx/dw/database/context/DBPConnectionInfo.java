package vn.tcx.dw.database.context;

import lombok.Data;
import vn.tcx.dw.database.model.DBPDriver;
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
}
