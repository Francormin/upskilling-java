package com.example.homeworkdesignpatterns.service.singleton;

public class ServiceConfig {

    private static ServiceConfig instance;

    private ServiceConfig() {
    }

    public static synchronized ServiceConfig getInstance() {
        if (instance == null) {
            instance = new ServiceConfig();
        }
        return instance;
    }

}
