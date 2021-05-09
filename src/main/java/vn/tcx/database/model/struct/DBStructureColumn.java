package vn.tcx.database.model.struct;


import lombok.Data;

/**
 * Define structure column
 * 
 * @author hieuvv
 * @since 1.0
 * @created 04/11/2020 10:23:06
 */
@Data
public class DBStructureColumn {
    
    private String tableName;

    private String name;

    private String dataType;
    
    private Boolean isIdentity;

    private Boolean isPrimaryKey;
    
    private Boolean isForeignKey;

    private Boolean isNull;

    private Boolean isUnique;

    private String refTable;
}
