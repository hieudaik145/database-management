package vn.tcx.database.model.struct;


import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DBStructDsStore {
    private String name;
    private String size;
    private List<DBStructureTable> struct = new ArrayList<>();
}
