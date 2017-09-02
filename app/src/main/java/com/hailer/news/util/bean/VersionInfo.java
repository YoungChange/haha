package com.hailer.news.util.bean;

/**
 * Created by moma on 17-9-1.
 */

public class VersionInfo {
    private String id;
    private String version;
    private String description;
    private String app_size;
    private String name;
    private String device_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppSize() {
        return app_size;
    }

    public void setAppSize(String appSize) {
        this.app_size = appSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceType() {
        return device_type;
    }

    public void setDeviceType(String deviceType) {
        this.device_type = deviceType;
    }
}
