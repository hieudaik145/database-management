package vn.tcx.dw.database.auth;

import java.util.Properties;

import vn.tcx.dw.database.annotation.NotNull;
import vn.tcx.dw.database.exception.DBException;
import vn.tcx.dw.database.model.DBPConnectionInfo;

public interface DBAAuthModel<CREDENTIALS extends DBAAuthCredentials> {

    @NotNull
    CREDENTIALS createCredentials();

    /**
     * Create credentials from datasource configuration
     */
    @NotNull
    CREDENTIALS loadCredentials(@NotNull DBPConnectionInfo connectionInfo);

    /**
     * Save credentials into connection configuration
     */
    void saveCredentials(@NotNull DBPConnectionInfo configuration, @NotNull CREDENTIALS credentials);

    Object initAuthentication(DBPConnectionInfo connectionInfo, @NotNull Properties connProperties,@NotNull CREDENTIALS credentials) throws DBException;

    void endAuthentication(@NotNull DBPConnectionInfo connectionInfo, @NotNull Properties connProperties);
}
