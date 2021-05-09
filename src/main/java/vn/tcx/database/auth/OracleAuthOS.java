package vn.tcx.database.auth;

import java.util.Properties;

import vn.tcx.database.constant.StandardConstants;
import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;

public class OracleAuthOS extends AuthModelDatabaseNative<OracleAuthOSCredentials> {
    
    public static final OracleAuthOS INSTANCE = new OracleAuthOS();

    @Override
    public OracleAuthOSCredentials createCredentials() {
        return new OracleAuthOSCredentials();
    }

    @Override
    public OracleAuthOSCredentials loadCredentials(DBPConnectionInfo connectionInfo) {
        OracleAuthOSCredentials credentials = super.loadCredentials(connectionInfo);
        credentials.setUserName(null);
        credentials.setUserPassword(null);
        return super.loadCredentials(connectionInfo);
    }

    @Override
    public Object initAuthentication(DBPConnectionInfo connectionInfo, Properties connectProps,
            OracleAuthOSCredentials credentials) throws DBException {
        connectProps.put("v$session.osuser", System.getProperty(StandardConstants.ENV_USER_NAME));
        return super.initAuthentication(connectionInfo, connectProps, credentials);
    }

}
