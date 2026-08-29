package com.mcmanuel;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record ApplicationConfiguration(
        String exchangeName
) {
}
