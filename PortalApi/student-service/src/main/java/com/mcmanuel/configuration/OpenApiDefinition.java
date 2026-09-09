package com.mcmanuel.configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                version = "1.0.0",
                contact = @Contact(
                        name = "mcmanuel",
                        url = "github.com/mcmanuel295",
                        email = "mcmanuel755@gmail.com"
                ) ,
                title = "Open API Specification",
                description = "Open API Documentation for Spring Project",
                summary = "The API documentation for student service"
        ),
        servers = {
                @Server(
                        description = "local ENV",
                        url = "http://localhost:8080"
                )
        }
)


@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth authentication",
        bearerFormat = "JWT",

        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)

public class OpenApiDefinition {

}

