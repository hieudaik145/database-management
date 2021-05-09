package vn.tcx.database.context.ssl;

import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.DBWHandlerConfiguration;

public class SSLHandlerImpl implements DBWNetworkHandler {

    @Override
    public DBPConnectionInfo initializeHandler(DBWHandlerConfiguration configuration,
            DBPConnectionInfo connectionInfo) {
        return null;
    }

}
