package vn.tcx.dw.database;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vn.tcx.dw.database.context.JDBCDataSource;
import vn.tcx.dw.database.context.MySQLDataSource;
import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.DBPDriver;
import vn.tcx.dw.database.model.enums.DBType;
import vn.tcx.dw.database.model.struct.DBStructDsStore;

class MySQLDataSourceIT {

    private DBPConnectionInfo connectionInfo;

    private JDBCDataSource jdbcDataSource;

    @BeforeEach
    void runBare() {

        connectionInfo = new DBPConnectionInfo();
        connectionInfo.setDatabaseName("staging");
        connectionInfo.setHostName("localhost");
        connectionInfo.setHostPort("3306");
        connectionInfo.setUserName("root");
        connectionInfo.setUserPassword("");
        DBPDriver drive = new DBPDriver();
        drive.setDriverClassName("org.mariadb.jdbc.Driver");
        connectionInfo.setDriver(drive);
        connectionInfo.setType(DBType.MARIDB);

        jdbcDataSource = new MySQLDataSource(connectionInfo);
    }

    @Test
    void testConnectionMysql() throws DBException {
        try (Connection conn = jdbcDataSource.openConnection()) {
            assertNotNull(conn);
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getStructDatabaseMysql() {

        DBStructDsStore result = jdbcDataSource.getStoreInformation();

        assertNotNull(result);
    }
}
