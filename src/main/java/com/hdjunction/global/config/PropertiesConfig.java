package com.hdjunction.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:hdjunction.properties")
@ConfigurationProperties(prefix = "hdjunction")
public class PropertiesConfig {
    private String protocolType;
    private String host;
    private Integer adminPort;
}
