package vn.tcx.database.auth;

public interface DBAAuthCredentials {

    boolean isComplete();

    String[] getMissingProperties();
}
