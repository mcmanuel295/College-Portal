package com.mcmanuel;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class AdminApplication {
    private final RabbitAdmin rabbitAdmin;
    private final ApplicationConfiguration appConfig;

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> rabbitAdmin.declareExchange( new DirectExchange(appConfig.exchangeName()));
    }
}