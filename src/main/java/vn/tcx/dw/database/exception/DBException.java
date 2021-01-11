package vn.tcx.dw.database.exception;

import java.sql.SQLException;

public class DBException extends Exception {

    public static final int ERROR_CODE_NONE = -1;

    private final boolean hasMessage;

    public DBException(String message) {
        super(message);
        this.hasMessage = true;
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
        this.hasMessage = message != null;
    }

    public boolean hasMessage() {
        return hasMessage;
    }

    public int getErrorCode() {
        Throwable cause = getCause();
        if (cause instanceof SQLException) {
            return ((SQLException) cause).getErrorCode();
        } else if (cause instanceof DBException) {
            return ((DBException) cause).getErrorCode();
        } else {
            return ERROR_CODE_NONE;
        }
    }

    /**
     * SQL state or other standard error code. For JDBC/SQL drivers it refers to
     * SQL99 state or XOpen state
     */
    public String getDatabaseState() {
        Throwable cause = getCause();
        if (cause instanceof SQLException) {
            return ((SQLException) cause).getSQLState();
        } else if (cause instanceof DBException) {
            return ((DBException) cause).getDatabaseState();
        } else {
            return null;
        }
    }

}