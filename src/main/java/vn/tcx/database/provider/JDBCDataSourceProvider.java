package vn.tcx.database.provider;

import vn.tcx.database.model.DBPConnectionInfo;

public abstract class JDBCDataSourceProvider {

     abstract String getConnectionURL(DBPConnectionInfo connectionInfo);
}
