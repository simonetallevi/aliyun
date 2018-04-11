package com.simone.aliyun.core.config;

import lombok.Data;

import java.util.Properties;

@Data
public class Secret {

    private String accessKeyId;

    private String accessKeySecret;

    public static Secret getSecret(Properties properties) {
        Secret secret = new Secret();
        secret.setAccessKeyId(properties.getProperty("accessKeyId"));
        secret.setAccessKeySecret(properties.getProperty("accessKeySecret"));
        return secret;
    }
}
