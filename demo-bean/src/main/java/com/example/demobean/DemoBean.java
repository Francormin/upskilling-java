package com.example.demobean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DemoBean implements InitializingBean, DisposableBean {

    public DemoBean() {
        System.out.println("DemoBean constructor");
    }

    @PostConstruct
    public void customInit() {
        System.out.println("DemoBean: @PostConstruct - customInit");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DemoBean: InitializingBean - afterPropertiesSet");
    }

    @PreDestroy
    public void customDestroy() {
        System.out.println("DemoBean: @PreDestroy - customDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DemoBean: DisposableBean - destroy");
    }

}
