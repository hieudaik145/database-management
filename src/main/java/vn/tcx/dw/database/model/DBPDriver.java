package vn.tcx.dw.database.model;

import java.util.Map;

import lombok.Data;

/**
 * class DBPDriver
 * 
 * @author hieuvv
 * @since 1.0
 * @created 07/01/2021 16:15:25
 */
@Data
public class DBPDriver {

    private String id;

    private String driverClassName;

    private int majorVersion;

    private String sampleURL;

    private String defaultPort;

    private Map<String, Object> connectionProperties;

}
