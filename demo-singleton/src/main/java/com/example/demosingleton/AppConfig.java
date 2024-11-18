package com.example.demosingleton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MiSingleton miSingleton() {
        return new MiSingleton();
    }

}
