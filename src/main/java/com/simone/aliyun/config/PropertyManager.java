package com.simone.aliyun.config;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static final String secretFile = "secret.properties";

    private static final String configFile = "config.properties";

    @Getter
    private final Secret secret;

    @Getter
    private final Config config;

    public PropertyManager() {
        secret = Secret.getSecret(loadProperties(secretFile));
        config = Config.getConfig(loadProperties(configFile));
    }

    @SneakyThrows(IOException.class)
    private Properties loadProperties(String fileName) {
        InputStream is = PropertyManager.class.getClassLoader().getResourceAsStream(fileName);
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }
}
