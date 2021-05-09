package vn.tcx.database.context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.DBWHandlerConfiguration;
import vn.tcx.database.model.struct.DBStructDsStore;
import vn.tcx.database.model.struct.DBStructureColumn;
import vn.tcx.database.model.struct.DBStructureTable;
import vn.tcx.database.provider.MySQLDataSourceProvider;
import vn.tcx.database.utils.CommonUtils;

/**
 * class MySQLDataSource
 * 
 * @author hieuvv
 * @since 1.0
 * @created 07/01/2021 16:26:21
 */
@Getter
@Setter
@Slf4j
public class MySQLDataSource extends JDBCDataSource {

    public MySQLDataSource(DBPConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    @Override
    protected Map<String, String> getInternalConnectionProperties() {

        Map<String, String> props = new LinkedHashMap<>(MySQLDataSourceProvider.getConnectionProps());

        DBWHandlerConfiguration ssl = connectionInfo.getSsl();

        // build data ssl
        if (ssl != null && ssl.isEnabled()) {
            initSSL(props, ssl);
        } else {
            // Newer MySQL servers/connectors requires explicit SSL disable
            props.put("useSSL", "false");
        }

        props.put("serverTimezone", "UTC");

        return props;
    }

    private void initSSL(Map<String, String> props, DBWHandlerConfiguration sslConfig) {
        
        
    }

    @Override
    public DBStructDsStore getStoreInformation() {

        try (Connection conn = openConnection(); Statement stmt = conn.createStatement();) {
            String storeName = connectionInfo.getDatabaseName();
            if (CommonUtils.isNotEmpty(storeName)) {
                DBStructDsStore store = new DBStructDsStore();
                store.setName(storeName);
                store.setStruct(getTableInformation(storeName,conn, stmt));
                return store;
            } else {
                log.error("storeName is empty");
            }

        } catch (SQLException | DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    List<DBStructureTable> getTableInformation(String storeName,Connection conn, Statement stmt) {

        List<DBStructureTable> structTableList = new ArrayList<>();
        String sql = "select t.TABLE_NAME, t.DATA_LENGTH from information_schema.TABLES t where t.TABLE_SCHEMA  = '"
                + storeName + "' order by t.TABLE_NAME";
        log.debug("Execute get information table sql : {}", sql);
        try (ResultSet rs = stmt.executeQuery(
                sql)) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                DBStructureTable structTable = new DBStructureTable();
                structTable.setStoreName(storeName);
                structTable.setTableName(tableName);
                structTable.setSize(rs.getString("DATA_LENGTH"));
                structTable.setColumnList(getColumnInformation(storeName, tableName,conn, stmt));
                structTableList.add(structTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return structTableList;
    }

    @Override
    List<DBStructureColumn> getColumnInformation(String storeName, String tableName,Connection conn, Statement stmt) {
        List<DBStructureColumn> structColumnList = new ArrayList<>();
        String sql = "SELECT c.COLUMN_NAME, c.COLUMN_TYPE,"
                + " EXTRA, IS_NULLABLE, tc.CONSTRAINT_TYPE, kcu.REFERENCED_TABLE_NAME as 'TABLE_REF'"
                + " FROM information_schema.`COLUMNS` c" + " left join information_schema.KEY_COLUMN_USAGE kcu"
                + " on c.COLUMN_NAME = kcu.COLUMN_NAME" + " and kcu.TABLE_NAME = c.TABLE_NAME"
                + " and kcu.TABLE_SCHEMA = 'nds'" + " left join information_schema.TABLE_CONSTRAINTS tc"
                + " on tc.CONSTRAINT_NAME= kcu.CONSTRAINT_NAME" + " and tc.TABLE_NAME = kcu.TABLE_NAME"
                + " and tc.CONSTRAINT_SCHEMA = kcu.CONSTRAINT_SCHEMA" + " where c.TABLE_SCHEMA = '" + storeName
                + "' and c.TABLE_NAME = '" + tableName + "' order by c.ORDINAL_POSITION";
        log.debug("Execute query get information column : {}", sql);
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DBStructureColumn column = new DBStructureColumn();
                column.setTableName(tableName);
                column.setName(rs.getString("COLUMN_NAME"));
                column.setDataType(rs.getString("COLUMN_TYPE"));
                column.setIsIdentity(rs.getString("EXTRA").equals("auto_increment"));
                column.setIsNull(rs.getString("IS_NULLABLE").equals("YES"));
                String constraintType = rs.getString("CONSTRAINT_TYPE");
                if (Objects.nonNull(constraintType)) {
                    column.setIsPrimaryKey(constraintType.equals("PRIMARY KEY"));
                    column.setIsForeignKey(constraintType.equals("FOREIGN KEY"));
                    column.setIsUnique(constraintType.equals("UNIQUE"));
                } else {
                    column.setIsPrimaryKey(false);
                    column.setIsForeignKey(false);
                    column.setIsUnique(false);
                }
                column.setRefTable(rs.getString("TABLE_REF"));
                structColumnList.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return structColumnList;
    }
}
