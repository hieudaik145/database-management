package vn.tcx.database;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vn.tcx.database.context.JDBCDataSource;
import vn.tcx.database.context.JDBCDataSourceFactory;
import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.DBPDriver;
import vn.tcx.database.model.enums.DBAuthType;
import vn.tcx.database.model.enums.DBType;
import vn.tcx.database.model.struct.DBStructDsStore;

class OracleDataSourceIT {

    private JDBCDataSource jdbcDataSource;

    @BeforeEach
    void initData() {

        DBPConnectionInfo connectionInfo = new DBPConnectionInfo();

        connectionInfo.setHostName("192.168.1.238");
        connectionInfo.setHostPort("1521");
        connectionInfo.setUserName("c##qa_dng_nam");
        connectionInfo.setUserPassword("1");
        connectionInfo.setDatabaseName("ORCL");
        connectionInfo.setType(DBType.ORACLE);
        connectionInfo.setAuthType(DBAuthType.DATABASE_NATIVE);
        DBPDriver driver = new DBPDriver();
        driver.setDriverClassName("oracle.jdbc.OracleDriver");
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

    @Test
    void testStructureOracle() {

        DBStructDsStore store = jdbcDataSource.getStoreInformation();

        assertNotNull(store);
    }

}
