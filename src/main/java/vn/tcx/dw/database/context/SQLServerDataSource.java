package vn.tcx.dw.database.context;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.DBPSSL;
import vn.tcx.dw.database.model.enums.SQLServerAuthentication;
import vn.tcx.dw.database.model.struct.DBStructDsStore;
import vn.tcx.dw.database.model.struct.DBStructureColumn;
import vn.tcx.dw.database.model.struct.DBStructureTable;
import vn.tcx.dw.database.utils.SQLServerUtils;

public class SQLServerDataSource extends JDBCDataSource {

    public SQLServerDataSource(DBPConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    @Override
    protected Properties getAllConnectionProperties() throws DBException {

        Properties properties = super.getAllConnectionProperties();

        fillConnectionProperties(properties);

        SQLServerAuthentication authSchema = SQLServerUtils.detectAuthSchema(connectionInfo);

        authSchema.getInitializer().initializeAuthentication(connectionInfo, properties);

        DBPSSL ssl = connectionInfo.getSsl();

        if (ssl != null && ssl.isEnable()) {
            initSSL(ssl, properties);
        }

        return properties;
    }

    private void initSSL(DBPSSL ssl, Properties properties) {

    }

    @Override
    public DBStructDsStore getStoreInformation() {
        return null;
    }

    @Override
    List<DBStructureTable> getTableInformation(String storeName, Connection conn, Statement stmt) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    List<DBStructureColumn> getColumnInformation(String storeName, String tableName, Connection conn, Statement stmt) {
        // TODO Auto-generated method stub
        return null;
    }

}
