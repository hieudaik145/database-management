package vn.tcx.database.context;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import vn.tcx.database.auth.AuthModelDatabaseNative;
import vn.tcx.database.auth.DBAAuthCredentials;
import vn.tcx.database.auth.DBAAuthModel;
import vn.tcx.database.auth.OracleAuthOS;
import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;
import vn.tcx.database.model.DBPDriver;
import vn.tcx.database.model.enums.DBAuthType;
import vn.tcx.database.model.enums.DBType;
import vn.tcx.database.model.struct.DBStructDsStore;
import vn.tcx.database.model.struct.DBStructureColumn;
import vn.tcx.database.model.struct.DBStructureTable;
import vn.tcx.database.provider.MySQLDataSourceProvider;
import vn.tcx.database.provider.OracleDataSourceProvider;
import vn.tcx.database.provider.SQLServerProvider;
import vn.tcx.database.utils.CommonUtils;

/**
 * Class JDBCDataSourceProvider
 * 
 * @author hieuvv
 * @since 1.0
 * @created 07/01/2021 16:58:16
 */
@Slf4j
public abstract class JDBCDataSource {

    protected DBPConnectionInfo connectionInfo;

    protected JDBCDataSource(DBPConnectionInfo connectionInfo) {
        super();
        this.connectionInfo = connectionInfo;
    }

    protected Map<String, String> getInternalConnectionProperties() {
        return null;
    }

    /**
     * Open connection
     * 
     * @throws DBException
     */
    public Connection openConnection() throws DBException {
        String url = getConnectionURL();
        DBPDriver driver = connectionInfo.getType().getDriver();
        Driver driverInstance = null;
        try {
            driverInstance = (Driver) Class.forName(driver.getDriverClassName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties connectProps = getAllConnectionProperties();
        Connection[] connection = new Connection[1];
        try {
            if (driverInstance != null) {
                try {
                    if (!driverInstance.acceptsURL(url)) {
                        log.error("Bad url: {}", url);
                    }
                } catch (Exception e) {
                    log.debug("Error in " + driverInstance.getClass().getName() + ".acceptsURL() -" + url + " {}",
                            e.getCause());
                }
            }
            final Driver driverInstanceFinal = driverInstance;
            try {
                DBAAuthModel authModel = getAuthMode(connectionInfo.getAuthType());
                DBAAuthCredentials credentials = authModel.loadCredentials(connectionInfo);
                authModel.initAuthentication(connectionInfo, connectProps, credentials);
            } catch (DBException e) {
                throw new DBException("Authentication error: " + e.getMessage(), e);
            }
            if (driverInstanceFinal == null) {
                connection[0] = DriverManager.getConnection(url, connectProps);
            } else {
                connection[0] = driverInstanceFinal.connect(url, connectProps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection[0];
    }

    protected Properties getAllConnectionProperties() throws DBException {

        // Set properties
        Properties connectProps = new Properties();

        // Use properties defined by datasource itself
        Map<String, String> internalProps = getInternalConnectionProperties();
        if (internalProps != null) {
            connectProps.putAll(internalProps);
        }

        fillConnectionProperties(connectProps);

        return connectProps;
    }

    protected void fillConnectionProperties(Properties connectProps) {
        // Use driver properties
        final Map<String, Object> driverProperties = connectionInfo.getType().getDriver().getConnectionProperties();
        if (driverProperties != null) {
            for (Map.Entry<String, Object> prop : driverProperties.entrySet()) {
                connectProps.setProperty(prop.getKey(), CommonUtils.toString(prop.getValue()));
            }
        }
        if (connectionInfo.getProperties() != null) {
            for (Map.Entry<String, String> prop : connectionInfo.getProperties().entrySet()) {
                connectProps.setProperty(CommonUtils.toString(prop.getKey()), CommonUtils.toString(prop.getValue()));
            }
        }
    }

    protected String getConnectionURL() {
        String url = connectionInfo.getUrl();
        if (CommonUtils.isEmpty(url)) {
            DBType type = connectionInfo.getType();
            if (type == DBType.ORACLE) {
                url = OracleDataSourceProvider.getConnectionURL(connectionInfo);
            } else if (type == DBType.SQL_SERVER) {
                url = SQLServerProvider.getConnectionStringURL(connectionInfo);
            } else {
                url = MySQLDataSourceProvider.getdConnectionStringURL(connectionInfo);
            }
        }
        return url;
    }

    private DBAAuthModel<?> getAuthMode(DBAuthType authType) {

        if (authType == DBAuthType.OS_AUTHENTICATION) {
            return OracleAuthOS.INSTANCE;
        }

        return AuthModelDatabaseNative.INSTANCE;
    }

    public abstract DBStructDsStore getStoreInformation();

    abstract List<DBStructureTable> getTableInformation(String storeName, Connection conn, Statement stmt);

    abstract List<DBStructureColumn> getColumnInformation(String storeName, String tableName, Connection conn,
            Statement stmt);

}
