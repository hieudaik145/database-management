package vn.tcx.dw.database.auth;

public interface DBAAuthCredentials {

    boolean isComplete();

    String[] getMissingProperties();
}
