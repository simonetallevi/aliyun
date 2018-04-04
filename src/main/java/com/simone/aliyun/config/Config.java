package com.simone.aliyun.config;

import lombok.Data;

import java.util.Properties;

@Data
public class Config {

    private String ossendpoint;

    private String mnsendpoint;

    private String bucketName;

    public static Config getConfig(Properties properties) {
        Config config = new Config();
        config.setOssendpoint(properties.getProperty("oss-endpoint"));
        config.setMnsendpoint(properties.getProperty("mns-endpoint"));
        config.setBucketName(properties.getProperty("bucketName"));
        return config;
    }
}
