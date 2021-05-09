package vn.tcx.database;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import vn.tcx.database.context.JDBCDataSource;
import vn.tcx.database.context.SQLServerDataSource;
import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.DBPDriver;
import vn.tcx.database.model.enums.DBType;

class SQLServerDataSourceIT {

    private DBPConnectionInfo connectionInfo;

    private JDBCDataSource jdbcDataSource;

    @BeforeEach
    void runBare() {

        connectionInfo = new DBPConnectionInfo();
        connectionInfo.setDatabaseName("master");
        connectionInfo.setHostName("localhost");
        connectionInfo.setHostPort("1433");
        connectionInfo.setUserName("sa");
        connectionInfo.setUserPassword("Vovanhieu123");
        connectionInfo.setType(DBType.SQL_SERVER);
        jdbcDataSource = new SQLServerDataSource(connectionInfo);
    }

    @Test
    void testConnection() throws DBException {
        try (Connection conn = jdbcDataSource.openConnection()) {
            System.out.println(conn);
            assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
