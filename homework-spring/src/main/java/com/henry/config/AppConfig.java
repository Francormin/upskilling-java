package com.henry.config;

import com.henry.service.TaskServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("taskService")
    public TaskServiceImpl getTaskService() {
        return new TaskServiceImpl();
    }

}
