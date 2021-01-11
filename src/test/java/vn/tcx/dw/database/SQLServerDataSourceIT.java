package vn.tcx.dw.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vn.tcx.dw.database.context.JDBCDataSource;
import vn.tcx.dw.database.context.SQLServerDataSource;
import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.DBPDriver;

class SQLServerDataSourceIT {

    private DBPConnectionInfo connectionInfo;

    private JDBCDataSource jdbcDataSource;

    @BeforeEach
    void runBare() {

        connectionInfo = new DBPConnectionInfo();
        connectionInfo.setDatabaseName("");
        connectionInfo.setHostName("localhost");
        connectionInfo.setHostPort("3306");
        connectionInfo.setUserName("root");
        connectionInfo.setUserPassword("");

        DBPDriver drive = new DBPDriver();
        drive.setDriverClassName("com.mysql.jdbc.Driver");
        connectionInfo.setDriver(drive);

        jdbcDataSource = new SQLServerDataSource(connectionInfo);
    }

    @Test
    public void testConnectionMysql() throws DBException {
        try (Connection conn = jdbcDataSource.openConnection()) {
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
