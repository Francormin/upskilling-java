package com.example.homeworkbean;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class HomeworkbeanApplication {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(HomeworkbeanApplication.class);

        System.out.println();
        applicationContext.getBean(DemoBean.class);

        System.out.println();
        applicationContext.getBean(DemoBean.class)
            .customDestroy();

        System.out.println();
        applicationContext.getBean(DemoBean.class)
            .doSomething();

        System.out.println();
        applicationContext.getBean(DemoBean.class)
            .destroy();
    }

}
