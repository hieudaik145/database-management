package vn.tcx.database.context.ssl;

import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.DBWHandlerConfiguration;

public interface DBWNetworkHandler {
    
    DBPConnectionInfo initializeHandler(DBWHandlerConfiguration configuration, DBPConnectionInfo connectionInfo);

}
