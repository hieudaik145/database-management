package vn.tcx.dw.database.provider;

import vn.tcx.dw.database.model.DBPConnectionInfo;

public abstract class JDBCDataSourceProvider {

     abstract String getConnectionURL(DBPConnectionInfo connectionInfo);
}
