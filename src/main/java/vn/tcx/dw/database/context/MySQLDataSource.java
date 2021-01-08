package vn.tcx.dw.database.context;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import vn.tcx.dw.database.utils.CommonUtils;

/**
 * class MariadbUtil
 * 
 * @author hieuvv
 * @since 1.0
 * @created 07/01/2021 16:26:21
 */
@Getter
@Setter
public class MySQLDataSource extends JDBCDataSource {

    public MySQLDataSource(DBPConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    /**
     * Open connection info mysql
     */
    public Connection getConnection() {

        Connection conn = null;

        return conn;
    }

    private String getConnectionURL() {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql://").append(connectionInfo.getHostName());
        if (!CommonUtils.isEmpty(connectionInfo.getHostPort())) {
            url.append(":").append(connectionInfo.getHostPort());
        }
        url.append("/");
        if (!CommonUtils.isEmpty(connectionInfo.getDatabaseName())) {
            url.append(connectionInfo.getDatabaseName());
        }

        return url.toString();
    }

    @Override
    protected Map<String, String> getInternalConnectionProperties() {
        // TODO Auto-generated method stub
        return null;
    }
}
