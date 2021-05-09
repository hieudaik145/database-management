package vn.tcx.database.model.struct;

import java.util.List;

import lombok.Data;

/**
 * Define structure table
 * 
 * @author hieuvv
 * @since 1.0
 * @created 04/11/2020 10:22:08
 */
@Data
public class DBStructureTable {

    private String storeName;

    private String tableName;

    private Long numberRecord;

    private String size;

    private List<DBStructureColumn> columnList;

}
