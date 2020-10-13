package com.sengou.upload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sengou.file")
public class  SegouFileConfiguration {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SegouFileConfiguration() {
    }

    public SegouFileConfiguration(String path) {
        this.path = path;
    }
}
