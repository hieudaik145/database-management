package vn.tcx.database.model.enums;

import lombok.Getter;
import vn.tcx.database.model.DBPDriver;

@Getter
public enum DBType {

    MYSQL(getDriverMySQL()), MARIADB(getDriverMariaDB()), ORACLE(getDriverOracle()), SQL_SERVER(getDriverSQLServer());

    private DBPDriver driver;

    DBType(DBPDriver driver) {
        this.driver = driver;
    }

    private static DBPDriver getDriverMariaDB() {
        DBPDriver driver = new DBPDriver();
        driver.setDriverClassName("org.mariadb.jdbc.Driver");
        return driver;
    }

    private static DBPDriver getDriverMySQL() {
        DBPDriver driver = new DBPDriver();
        driver.setDriverClassName("com.mysql.Driver");
        return driver;
    }

    private static DBPDriver getDriverOracle() {
        DBPDriver driver = new DBPDriver();
        driver.setDriverClassName("oracle.jdbc.OracleDriver");
        return driver;
    }

    private static DBPDriver getDriverSQLServer() {
        DBPDriver driver = new DBPDriver();
        driver.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        driver.setSampleURL("jdbc:sqlserver://<server>:<port>;databaseName=AdventureWorks;user=<user>;password=<password>");
        driver.setId("generic");
        return driver;
    }

}
