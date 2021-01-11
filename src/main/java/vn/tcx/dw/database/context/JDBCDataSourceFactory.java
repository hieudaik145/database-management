package vn.tcx.dw.database.context;

import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.enums.DBType;

/**
 * JDBCDataSourceFactory Utils
 * 
 * @author hieuvv
 * @since 1.0
 * @created 09/01/2021 16:56:20
 */
public class JDBCDataSourceFactory {

    public static JDBCDataSource getJDBCDataSource(DBPConnectionInfo connectionInfo) {

        DBType dbType = connectionInfo.getType();

        if (dbType == DBType.ORACLE) {
            return new OracleDataSource(connectionInfo);

        } else if (dbType == DBType.SQL_SERVER) {
            return new SQLServerDataSource(connectionInfo);
        } else {
            return new MySQLDataSource(connectionInfo);
        }
    }

}
