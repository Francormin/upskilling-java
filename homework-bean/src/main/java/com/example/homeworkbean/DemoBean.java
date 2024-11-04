package com.example.homeworkbean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DemoBean implements InitializingBean, DisposableBean {

    private static int instanceCount = 0;

    public DemoBean() {
        instanceCount++;
    }

    @PostConstruct
    public void customInit() {
        System.out.println("DemoBean " + instanceCount + ": @PostConstruct - inicialización personalizada del Bean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DemoBean " + instanceCount + ": InitializingBean - afterPropertiesSet");
    }

    public void doSomething() {
        System.out.println("DemoBean " + instanceCount + ": doSomething");
    }

    @PreDestroy
    public void customDestroy() {
        System.out.println("DemoBean " + instanceCount + ": @PreDestroy - destrucción personalizada del Bean");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DemoBean " + instanceCount + ": DisposableBean - destroy");
    }

}
