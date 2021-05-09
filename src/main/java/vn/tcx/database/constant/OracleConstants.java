package vn.tcx.database.constant;

public class OracleConstants {

    public static final int DEFAULT_PORT = 1521;

    public static final String SCHEMA_SYS = "SYS";
    public static final String VIEW_ALL_SOURCE = "ALL_SOURCE";
    public static final String VIEW_DBA_SOURCE = "DBA_SOURCE";
    public static final String VIEW_DBA_TAB_PRIVS = "DBA_TAB_PRIVS";

    public static final String[] SYSTEM_SCHEMAS = {
        "CTXSYS",
        "DBSNMP",
        "DMSYS",
        "EXFSYS",
        "IX",
        "MDDATA",
        "MDSYS",
        "DMSYS",
        "MGMT_VIEW",
        "OLAPSYS",
        "ORDPLUGINS",
        "ORDSYS",
        "ORDDATA",
        "SI_INFORMTN_SCHEMA",
        "OWBSYS",
        "OWBSYS_AUDIT",
        "OUTLN",
        "ORACLE_OCM",
        "SYS",
        "SYSMAN",
        "SYSTEM",
        "TSMSYS",
        "WMSYS",
        "XDB",
    };

    public static final String PROP_CONNECTION_TYPE = DBConstants.INTERNAL_PROP_PREFIX + "connection-type@";
    public static final String PROP_SID_SERVICE = DBConstants.INTERNAL_PROP_PREFIX + "sid-service@";
    public static final String PROP_CONNECTION_TARGET = "connection_target";
    public static final String PROP_DRIVER_TYPE = DBConstants.INTERNAL_PROP_PREFIX + "driver-type@";
    public static final String PROP_INTERNAL_LOGON = DBConstants.INTERNAL_PROP_PREFIX + "internal-logon@";
    public static final String PROP_TNS_PATH = DBConstants.INTERNAL_PROP_PREFIX + "tns-path@";
    public static final String PROP_AUTH_LOGON_AS = "oracle.logon-as";

    public static final String PROP_SESSION_LANGUAGE = DBConstants.INTERNAL_PROP_PREFIX + "session-language@";
    public static final String PROP_SESSION_TERRITORY = DBConstants.INTERNAL_PROP_PREFIX + "session-territory@";
    public static final String PROP_SESSION_NLS_DATE_FORMAT = DBConstants.INTERNAL_PROP_PREFIX + "session-nls-date-format@";
    public static final String PROP_SESSION_NLS_TIMESTAMP_FORMAT = "session-nls-timestamp-format";
    public static final String PROP_SESSION_NLS_LENGTH_FORMAT = "session-nls-length-format";
    public static final String PROP_SESSION_NLS_CURRENCY_FORMAT = "session-nls-currency-format";
    public static final String PROP_CHECK_SCHEMA_CONTENT = DBConstants.INTERNAL_PROP_PREFIX + "check-schema-content@";
    public static final String PROP_ALWAYS_SHOW_DBA = DBConstants.INTERNAL_PROP_PREFIX + "always-show-dba@";
    public static final String PROP_ALWAYS_USE_DBA_VIEWS = DBConstants.INTERNAL_PROP_PREFIX + "always-use-dba-views@";
    public static final String PROP_USE_RULE_HINT = DBConstants.INTERNAL_PROP_PREFIX + "use-rule-hint@";
    public static final String PROP_USE_META_OPTIMIZER = DBConstants.INTERNAL_PROP_PREFIX + "use-meta-optimizer@";
    public static final String PROP_METADATA_USE_SYS_SCHEMA = DBConstants.INTERNAL_PROP_PREFIX + "meta-use-sys-schema@";
    public static final String PROP_METADATA_USE_SIMPLE_CONSTRAINTS = DBConstants.INTERNAL_PROP_PREFIX + "meta-use-simple-constraints@";
    public static final String PROP_METADATA_USE_ALTERNATIVE_TABLE_QUERY = DBConstants.INTERNAL_PROP_PREFIX + "meta-use-alternative-table-query@";


    public static final String OS_AUTH_PROP = DBConstants.INTERNAL_PROP_PREFIX + "os-authentication@";

    public static final String DRIVER_TYPE_THIN = "THIN";
    public static final String DRIVER_TYPE_OCI = "OCI";

    public static final String USER_PUBLIC = "PUBLIC";

    public static final String YES = "YES";

    public static final String TYPE_NAME_XML = "XMLTYPE";
    public static final String TYPE_FQ_XML = "SYS.XMLTYPE";
    public static final String TYPE_NAME_GEOMETRY = "PUBLIC.SDO_GEOMETRY";
    public static final String TYPE_FQ_GEOMETRY = "MDSYS.SDO_GEOMETRY";
    public static final String TYPE_NAME_BFILE = "BFILE";
    public static final String TYPE_NAME_DATE = "DATE";
    public static final String TYPE_NAME_TIMESTAMP = "TIMESTAMP";
    public static final String TYPE_NUMBER = "NUMBER";
    public static final String TYPE_NAME_REFCURSOR = "REFCURSOR";

    public static final int TIMESTAMP_TYPE_LENGTH = 13;
    public static final int DATE_TYPE_LENGTH = 7;

    public static final String PROP_OBJECT_DEFINITION = "objectDefinitionText";
    public static final String PROP_OBJECT_BODY_DEFINITION = "extendedDefinitionText";

    public static final String COL_OWNER = "OWNER";
    public static final String COL_TABLE_NAME = "TABLE_NAME";
    public static final String COL_CONSTRAINT_NAME = "CONSTRAINT_NAME";
    public static final String COL_CONSTRAINT_TYPE = "CONSTRAINT_TYPE";

    public static final String XML_COLUMN_NAME = "XML";
    public static final String OBJECT_VALUE_COLUMN_NAME = "OBJECT_VALUE";

    public static final String PREF_EXPLAIN_TABLE_NAME = "oracle.explain.table";
    public static final String PREF_SUPPORT_ROWID = "oracle.support.rowid";
    public static final String PREF_DBMS_OUTPUT = "oracle.dbms.output";
    public static final String PREF_DBMS_READ_ALL_SYNONYMS = "oracle.read.all.synonyms";
    public static final String PREF_DISABLE_SCRIPT_ESCAPE_PROCESSING = "oracle.disable.script.escape";

    public static final String NLS_DEFAULT_VALUE = "Default";
    public static final String PREF_KEY_DDL_FORMAT = "oracle.ddl.format";
    public static final int MAXIMUM_DBMS_OUTPUT_SIZE = 1000000;

    public static final String VAR_ORA_HOME = "ORA_HOME";
    public static final String VAR_ORACLE_HOME = "ORACLE_HOME";
    public static final String VAR_TNS_ADMIN = "TNS_ADMIN";
    public static final String VAR_PATH = "PATH";
    public static final String VAR_ORACLE_NET_TNS_ADMIN = "oracle.net.tns_admin";

    public static final int DATA_TYPE_TIMESTAMP_WITH_TIMEZONE = 101;
    public static final int DATA_TYPE_TIMESTAMP_WITH_LOCAL_TIMEZONE = 102;
    public static final int DATA_TYPE_REFCURSOR = -10;

    /**
     * Oracle error codes
     */
    public static final int EC_FEATURE_NOT_SUPPORTED = 17023;
    public static final int EC_NO_RESULTSET_AVAILABLE = 17283;
    public static final int EC_PASSWORD_EXPIRED = 28001;
    public static final int NUMERIC_MAX_PRECISION = 38;

    /**
     * Connection type
     */
    public enum ConnectionType {
        BASIC,
        TNS,
        CUSTOM
    }

    public static final String XMLTYPE_CLASS_NAME = "oracle.xdb.XMLType";
    public static final String BFILE_CLASS_NAME = "oracle.sql.BFILE";
    public static final String TIMESTAMP_CLASS_NAME     = "oracle.sql.TIMESTAMP";
    public static final String TIMESTAMPTZ_CLASS_NAME   = "oracle.sql.TIMESTAMPTZ";
    public static final String TIMESTAMPLTZ_CLASS_NAME  = "oracle.sql.TIMESTAMPLTZ";

    public static final String PLAN_TABLE_DEFINITION =
        "create global temporary table ${TABLE_NAME} (\n" +
            "statement_id varchar2(30),\n" +
            "plan_id number,\n" +
            "timestamp date,\n" +
            "remarks varchar2(4000),\n" +
            "operation varchar2(30),\n" +
            "options varchar2(255),\n" +
            "object_node varchar2(128),\n" +
            "object_owner varchar2(30),\n" +
            "object_name varchar2(30),\n" +
            "object_alias varchar2(65),\n" +
            "object_instance numeric,\n" +
            "object_type varchar2(30),\n" +
            "optimizer varchar2(255),\n" +
            "search_columns number,\n" +
            "id numeric,\n" +
            "parent_id numeric,\n" +
            "depth numeric,\n" +
            "position numeric,\n" +
            "cost numeric,\n" +
            "cardinality numeric,\n" +
            "bytes numeric,\n" +
            "other_tag varchar2(255),\n" +
            "partition_start varchar2(255),\n" +
            "partition_stop varchar2(255),\n" +
            "partition_id numeric,\n" +
            "other long,\n" +
            "distribution varchar2(30),\n" +
            "cpu_cost numeric,\n" +
            "io_cost numeric,\n" +
            "temp_space numeric,\n" +
            "access_predicates varchar2(4000),\n" +
            "filter_predicates varchar2(4000),\n" +
            "projection varchar2(4000),\n" +
            "time numeric,\n" +
            "qblock_name varchar2(30),\n" +
            "other_xml clob\n" +
            ") on commit preserve rows";

}
