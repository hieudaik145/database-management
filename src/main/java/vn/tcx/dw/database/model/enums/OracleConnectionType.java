package vn.tcx.dw.database.model.enums;

public enum OracleConnectionType {
    SID("oracle connection sid"), SERVICE("oracle connection service");

    private final String title;

    OracleConnectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static OracleConnectionType getTypeForTitle(String title) {
        for (OracleConnectionType odt : values()) {
            if (title.equals(odt.getTitle())) {
                return odt;
            }
        }
        return SID;
    }
}