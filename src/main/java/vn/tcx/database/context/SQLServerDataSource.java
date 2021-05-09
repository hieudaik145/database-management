package vn.tcx.database.context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.enums.SQLServerAuthentication;
import vn.tcx.database.model.struct.DBStructDsStore;
import vn.tcx.database.model.struct.DBStructureColumn;
import vn.tcx.database.model.struct.DBStructureTable;
import vn.tcx.database.utils.SQLServerUtils;

public class SQLServerDataSource extends JDBCDataSource {
    
    private String schema;

    public SQLServerDataSource(DBPConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    @Override
    protected Properties getAllConnectionProperties() throws DBException {

        Properties properties = super.getAllConnectionProperties();

        fillConnectionProperties(properties);

        SQLServerAuthentication authSchema = SQLServerUtils.detectAuthSchema(connectionInfo);

        authSchema.getInitializer().initializeAuthentication(connectionInfo, properties);

        return properties;
    }

    @Override
    public DBStructDsStore getStoreInformation() {

        String storeName = connectionInfo.getDatabaseName();
        this.schema = storeName;
        try (Connection conn = openConnection(); Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT " + " SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA s"
                    + " where s.CATALOG_NAME = 'master'" + " and s.SCHEMA_NAME not in"
                    + " ('guest', 'INFORMATION_SCHEMA', 'sys', 'db_owner', 'db_accessadmin',"
                    + " 'db_securityadmin', 'db_ddladmin', 'db_backupoperator', 'db_datareader',"
                    + " 'db_datawriter', 'db_denydatareader', 'db_denydatawriter')");
            DBStructDsStore structStore = new DBStructDsStore();
            structStore.setName(storeName);
            structStore.setStruct(getTableInformation(storeName, conn, stmt));
            structStore.setStruct(new ArrayList<>());
            while (rs.next()) {
                List<DBStructureTable> tableList = getTableInformation(rs.getString(1), conn, stmt);
                structStore.getStruct().addAll(tableList);
            }
            return structStore;

        } catch (SQLException | DBException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    List<DBStructureTable> getTableInformation(String storeName, Connection conn, Statement stmtd) {

        List<DBStructureTable> target = new ArrayList<>();
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA  = '" + storeName + "' and TABLE_TYPE  = 'BASE TABLE'";
        ResultSet rs = null;
        try (Statement stmt = conn.createStatement()) {

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                DBStructureTable table = new DBStructureTable();
                String tableName = rs.getString("TABLE_NAME");
                table.setStoreName(schema);
                table.setTableName(storeName + "." + tableName);
                table.setColumnList(getColumnInformation(storeName, tableName, conn, stmt));
                target.add(table);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return target;
    }

    @Override
    List<DBStructureColumn> getColumnInformation(String storeName, String tableName, Connection conn, Statement stmtd) {
        List<DBStructureColumn> target = new ArrayList<>();
        String sql = "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"
                + storeName + "' AND TABLE_NAME = '" + tableName + "'";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                DBStructureColumn column = new DBStructureColumn();
                column.setTableName(storeName + "." + tableName);
                column.setName(rs.getString("COLUMN_NAME"));
                column.setDataType(rs.getString("DATA_TYPE"));
                column.setIsNull(rs.getString("IS_NULLABLE").equals("YES"));
                target.add(column);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return target;
    }

}
