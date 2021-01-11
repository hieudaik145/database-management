package vn.tcx.dw.database.model.struct;


import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DBStructDsStore {
    private Long id;
    private String name;
    private String description;
    private List<DBStructureTable> struct = new ArrayList<>();
}
