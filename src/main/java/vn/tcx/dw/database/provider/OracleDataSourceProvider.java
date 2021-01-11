package vn.tcx.dw.database.provider;

import vn.tcx.dw.database.annotation.NotNull;
import vn.tcx.dw.database.constant.OracleConstants;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.model.enums.OracleConnectionType;
import vn.tcx.dw.database.utils.CommonUtils;
import vn.tcx.dw.database.utils.JDBCURL;

public class OracleDataSourceProvider {

    public static String getConnectionURL(DBPConnectionInfo connectionInfo) {
        OracleConstants.ConnectionType connectionType = getConnectionType(connectionInfo);
        if (connectionType == OracleConstants.ConnectionType.CUSTOM) {
            return JDBCURL.generateUrlByTemplate(connectionInfo.getUrl(), connectionInfo);
        }
        StringBuilder url = new StringBuilder(100);
        url.append("jdbc:oracle:thin:@"); //$NON-NLS-1$
        String databaseName = CommonUtils.notEmpty(connectionInfo.getDatabaseName());
        if (connectionType != OracleConstants.ConnectionType.TNS) {
         // Basic connection info specified
            boolean isSID = OracleConnectionType.SID.name().equals(connectionInfo.getProviderProperty(OracleConstants.PROP_SID_SERVICE));
            if (!isSID) {
                url.append("//"); //$NON-NLS-1$
            }
            if (!CommonUtils.isEmpty(connectionInfo.getHostName())) {
                url.append(connectionInfo.getHostName());
            }
            if (!CommonUtils.isEmpty(connectionInfo.getHostPort())) {
                url.append(":"); //$NON-NLS-1$
                url.append(connectionInfo.getHostPort());
            }
            if (isSID) {
                url.append(":"); //$NON-NLS-1$
            } else {
                url.append("/"); //$NON-NLS-1$
            }
            if (!CommonUtils.isEmpty(databaseName)) {
                url.append(databaseName);
            }
        }
        return url.toString();
    }
    
    @NotNull
    private static OracleConstants.ConnectionType getConnectionType(DBPConnectionInfo connectionInfo) {
        OracleConstants.ConnectionType connectionType;
        String conTypeProperty = connectionInfo.getProviderProperty(OracleConstants.PROP_CONNECTION_TYPE);
        if (conTypeProperty != null) {
            connectionType = OracleConstants.ConnectionType.valueOf(CommonUtils.toString(conTypeProperty));
        } else {
            connectionType = OracleConstants.ConnectionType.BASIC;
        }
        return connectionType;
    }
}
