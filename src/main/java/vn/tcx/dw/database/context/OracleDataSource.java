package vn.tcx.dw.database.context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.struct.DBStructDsStore;
import vn.tcx.dw.database.model.struct.DBStructureColumn;
import vn.tcx.dw.database.model.struct.DBStructureTable;
import vn.tcx.dw.database.utils.CommonUtils;

@Slf4j
public class OracleDataSource extends JDBCDataSource {

    protected OracleDataSource(DBPConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    @Override
    public DBStructDsStore getStoreInformation() {
        try (Connection conn = openConnection(); Statement stmt = conn.createStatement();) {
            String storeName = connectionInfo.getDatabaseName();
            if (!CommonUtils.isNotEmpty(storeName)) {
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
        try (ResultSet rs = stmt
                .executeQuery("SELECT TABLE_NAME, NUM_ROWS FROM all_tables  WHERE owner='" + storeName + "'")) {
            DBStructureTable table = null;
            while (rs.next()) {
                
                table = new DBStructureTable();
                String tableName = rs.getString("TABLE_NAME");
                table.setTableName(tableName);
                table.setNumberRecord(rs.getLong("NUM_ROWS"));
                table.setColumnList(getColumnInformation(storeName, tableName, conn, stmt));
                target.add(table);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return target;
    }

    @Override
    List<DBStructureColumn> getColumnInformation(String storeName, String tableName, Connection conn, Statement stmt) {
        return null;
    }

}
