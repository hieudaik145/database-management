package vn.tcx.dw.database.auth;

import java.util.Properties;

import vn.tcx.dw.database.constant.DBConstants;
import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;
import vn.tcx.dw.database.utils.CommonUtils;

public class AuthModelDatabaseNative<CREDENTIALS extends AuthModelDatabaseNativeCredentials>
        implements DBAAuthModel<CREDENTIALS> {

    public static final String ID = "native";

    public static final AuthModelDatabaseNative INSTANCE = new AuthModelDatabaseNative();

    @Override
    public CREDENTIALS createCredentials() {
        return (CREDENTIALS) new AuthModelDatabaseNativeCredentials();
    }

    @Override
    public CREDENTIALS loadCredentials(DBPConnectionInfo connectionInfo) {
        CREDENTIALS credentials = createCredentials();
        credentials.setUserName(connectionInfo.getUserName());
        credentials.setUserPassword(connectionInfo.getUserPassword());
        return credentials;
    }

    @Override
    public void saveCredentials(DBPConnectionInfo configuration, CREDENTIALS credentials) {
        configuration.setUserName(credentials.getUserName());
        configuration.setUserPassword(credentials.getUserPassword());
    }

    @Override
    public Object initAuthentication(DBPConnectionInfo connectionInfo, Properties connectProps, CREDENTIALS credentials)
            throws DBException {

        String userName = credentials.getUserName();
        String userPassword = credentials.getUserPassword();

        if (!CommonUtils.isEmpty(userName)) {
            connectProps.put(DBConstants.DATA_SOURCE_PROPERTY_USER, userName);
        }
        if (!CommonUtils.isEmpty(userPassword)) {
            connectProps.put(DBConstants.DATA_SOURCE_PROPERTY_PASSWORD, userPassword);
        }

        return credentials;
    }

    @Override
    public void endAuthentication(DBPConnectionInfo connectionInfo, Properties connProperties) {

    }

}
