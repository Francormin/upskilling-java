package com.example.demobean;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemobeanApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemobeanApplication.class);
        DemoBean demoBean = context.getBean(DemoBean.class);
        context.close();
    }

}
