package vn.tcx.database.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import vn.tcx.database.annotation.NotNull;
import vn.tcx.database.annotation.Nullable;
import vn.tcx.database.utils.CommonUtils;
import vn.tcx.database.utils.GeneralUtils;

@Data
public class DBWHandlerConfiguration {

    public static final String PROP_HOST = "host";
    public static final String PROP_PORT = "port";

    private boolean enabled;
    private String userName;
    private String password;
    private boolean savePassword = true;
    private Map<String, Object> properties = new HashMap<>();

    @Nullable
    public Object getProperty(@NotNull String name) {
        return this.properties.get(name);
    }

    @Nullable
    public String getStringProperty(@NotNull String name) {
        return CommonUtils.toString(this.properties.get(name), null);
    }

    public int getIntProperty(@NotNull String name) {
        return CommonUtils.toInt(this.properties.get(name));
    }

    public boolean getBooleanProperty(@NotNull String name) {
        return this.getBooleanProperty(name, false);
    }

    public boolean getBooleanProperty(@NotNull String name, boolean defValue) {
        return CommonUtils.getBoolean(this.properties.get(name), defValue);
    }

    public void setProperty(@NotNull String name, @Nullable Object value) {
        if (value == null) {
            this.properties.remove(name);
        } else {
            this.properties.put(name, value);
        }
    }

    @NotNull
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(@NotNull Map<String, Object> properties) {
        this.properties.clear();
        this.properties.putAll(properties);
    }


    public boolean hasValuableInfo() {
        return !CommonUtils.isEmpty(userName) ||
            !CommonUtils.isEmpty(password) ||
            !CommonUtils.isEmpty(properties);
    }
}
