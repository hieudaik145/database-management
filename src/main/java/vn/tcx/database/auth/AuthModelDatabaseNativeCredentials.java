package vn.tcx.database.auth;

import lombok.Data;

@Data
public class AuthModelDatabaseNativeCredentials implements DBAAuthCredentials {


    public static final String PROP_USER_NAME = "userName";
    public static final String PROP_USER_PASSWORD = "userPassword";
    
    private String userName;
    private String userPassword;

    
    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public String[] getMissingProperties() {
        return null;
    }

}
