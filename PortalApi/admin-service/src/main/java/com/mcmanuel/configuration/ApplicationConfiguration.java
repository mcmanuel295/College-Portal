package com.mcmanuel.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record ApplicationConfiguration(
        String exchangeName
) {
}
