package vn.tcx.dw.database.model;

import java.sql.Connection;

import lombok.Data;

/**
 * Define basic database
 * 
 * @author hieuvv
 * @since 1.0
 * @created 07/01/2021 10:34:13
 */
@Data
public abstract class DBPConnectionConfiguration {

    // Variables
    public static final String VARIABLE_HOST = "host";
    public static final String VARIABLE_PORT = "port";
    public static final String VARIABLE_SERVER = "server";
    public static final String VARIABLE_DATABASE = "database";
    public static final String VARIABLE_USER = "user";
    public static final String VARIABLE_PASSWORD = "password";
    public static final String VARIABLE_URL = "url";

    protected String hostName;
    protected String hostPort;
    protected String serverName;
    protected String databaseName;
    protected String userName;
    protected String userPassword;
    protected String url;

    public abstract Connection getConnection();

}
