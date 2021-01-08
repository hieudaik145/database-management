package vn.tcx.dw.database.context;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Map;

import vn.tcx.dw.database.model.DBPDriver;

/**
 * Class JDBCDataSourceProvider
 * 
 * @author hieuvv
 * @since 1.0
 * @created 07/01/2021 16:58:16
 */
public abstract class JDBCDataSource {

    protected DBPConnectionInfo connectionInfo;

    public JDBCDataSource(DBPConnectionInfo connectionInfo) {
        super();
        this.connectionInfo = connectionInfo;
    }
    
    protected abstract Map<String, String> getInternalConnectionProperties();

    /**
     * Open connection
     */
    protected Connection openConnection(String url) {

        DBPDriver driver = connectionInfo.getDriver();

        try {
            
            Class.forName(driver.getDriverClassName(), true, loader);
            DriverManager.getConnection(url)
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return null;

    }
}
