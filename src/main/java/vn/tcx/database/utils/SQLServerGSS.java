package vn.tcx.database.utils;

import java.util.Properties;

import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

import vn.tcx.database.exception.DBException;
import vn.tcx.database.model.DBPConnectionInfo;

public class SQLServerGSS {

    public static void initCredentials(DBPConnectionInfo connectionInfo, Properties properties) throws DBException {
        if (!CommonUtils.isEmpty(connectionInfo.getUserName())) {
            try {
                GSSManager gssManager = GSSManager.getInstance();
                GSSName name = gssManager.createName(connectionInfo.getUserName(), GSSName.NT_USER_NAME);
                GSSCredential impersonatedUserCredential = gssManager.createCredential(name,
                        GSSCredential.DEFAULT_LIFETIME, (Oid) null, GSSCredential.ACCEPT_ONLY);
                properties.put("gsscredential", impersonatedUserCredential);
            } catch (GSSException e) {
                throw new DBException("Error initializing GSS", e);
            }
        }
    }
}
