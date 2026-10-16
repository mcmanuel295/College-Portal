package com.mcmanuel.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "course")
public record ApplicationConfiguration (
        String exchangeName
){
}
