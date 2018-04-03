package com.simone.aliyun.config;

import lombok.Data;

import java.util.Properties;

@Data
public class Config {

    private String endpoint;

    private String bucketName;

    private String producerId;

    private String ONSAddr;

    public static Config getConfig(Properties properties) {
        Config config = new Config();
        config.setEndpoint(properties.getProperty("endpoint"));
        config.setBucketName(properties.getProperty("bucketName"));
        config.setProducerId(properties.getProperty("producerId"));
        config.setONSAddr(properties.getProperty("ONSAddr"));
        return config;
    }
}
