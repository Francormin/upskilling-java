package com.example.homeworkorm.util;

public class ExceptionMessageUtil {

    public static String entityNotFound(String entityName, Long id) {
        return entityName + " with ID " + id + " not found";
    }

}
