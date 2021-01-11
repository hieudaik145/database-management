package vn.tcx.dw.database;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vn.tcx.dw.database.context.JDBCDataSource;
import vn.tcx.dw.database.context.JDBCDataSourceFactory;
import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.DBPDriver;
 class OracleDataSourceIT {

    private JDBCDataSource jdbcDataSource;

    @BeforeEach
    void initData() {

        DBPConnectionInfo connectionInfo = new DBPConnectionInfo();

        connectionInfo.setHostName("192.168.1.238");
        connectionInfo.setHostPort("1521");
        connectionInfo.setUserName("c##qa_dng_nam");
        connectionInfo.setUserPassword("1");
        connectionInfo.setDatabaseName("c##qa_dng_nam");
        DBPDriver driver = new DBPDriver();
        driver.setDriverClassName("oracle.jdbc.OracleDriver");
        connectionInfo.setDriver(driver);
        jdbcDataSource = JDBCDataSourceFactory.getJDBCDataSource(connectionInfo);

    }

    @Test
    void testConnectionOracle() {

        try (Connection conn = jdbcDataSource.openConnection()) {
            assertNotNull(conn);
            System.out.println(conn);
        } catch (SQLException | DBException e) {
            e.printStackTrace();
        }
    }

}
