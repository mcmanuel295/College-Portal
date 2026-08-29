package com.mcmanuel.configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student")
public record ApplicationConfiguration(
){}
