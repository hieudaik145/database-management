package vn.tcx.database.context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.struct.DBStructDsStore;
import vn.tcx.database.model.struct.DBStructureColumn;
import vn.tcx.database.model.struct.DBStructureTable;
import vn.tcx.database.utils.CommonUtils;

@Slf4j
public class OracleDataSource extends JDBCDataSource {

    protected OracleDataSource(DBPConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    @Override
    public DBStructDsStore getStoreInformation() {
        try (Connection conn = openConnection(); Statement stmt = conn.createStatement();) {
            String storeName = connectionInfo.getUserName();
            if (CommonUtils.isNotEmpty(storeName)) {
                DBStructDsStore store = new DBStructDsStore();
                store.setName(storeName);
                store.setStruct(getTableInformation(storeName, conn, stmt));
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
    List<DBStructureTable> getTableInformation(String storeName, Connection conn, Statement stmt) {

        List<DBStructureTable> target = new ArrayList<>();
        String sql = "SELECT TABLE_NAME, NUM_ROWS FROM all_tables  WHERE owner='" + storeName + "'";
        log.debug("Get information schema table: {}", sql);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                DBStructureTable table = new DBStructureTable();
                table.setStoreName(storeName);
                String tableName = rs.getString("TABLE_NAME");
                table.setTableName(tableName);
                table.setNumberRecord(rs.getLong("NUM_ROWS"));
                table.setColumnList(getColumnInformation(storeName, tableName, conn, stmt));
                target.add(table);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(rs)) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
    }

    @Override
    List<DBStructureColumn> getColumnInformation(String storeName, String tableName, Connection conn, Statement stmtd) {
        List<DBStructureColumn> columnList = new ArrayList<>();
        String sql = "SELECT COLUMN_NAME, DATA_TYPE,DATA_LENGTH, CHAR_COL_DECL_LENGTH, IDENTITY_COLUMN FROM all_tab_columns WHERE TABLE_NAME = '"
                + tableName + "' AND OWNER = '" + storeName + "'";
        log.debug("Get information schema column: {}", sql);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                DBStructureColumn column = new DBStructureColumn();
                column.setTableName(tableName);
                column.setName(rs.getString("COLUMN_NAME"));
                column.setDataType(rs.getString("DATA_TYPE"));
                column.setIsIdentity(rs.getString("IDENTITY_COLUMN").equals("YES"));
                columnList.add(column);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnList;
    }

}
