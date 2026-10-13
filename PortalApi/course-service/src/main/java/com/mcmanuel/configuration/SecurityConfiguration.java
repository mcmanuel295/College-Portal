package com.mcmanuel.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class SecurityConfiguration {

    @Bean
    public RequestInterceptor authInterceptior(){
        return template ->{
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attrs != null) {
                String auth = attrs.getRequest().getHeader("Authorization");
                if (auth != null) template.header("Authorization",auth);
            }
        };
    }
}
