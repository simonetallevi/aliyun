package com.simone.aliyun.config;

import lombok.Data;

import java.util.Properties;

@Data
public class Config {

    private String endpoint;

    private String bucketName;

    public static Config getConfig(Properties properties) {
        Config config = new Config();
        config.setEndpoint(properties.getProperty("endpoint"));
        config.setBucketName(properties.getProperty("bucketName"));
        return config;
    }
}
