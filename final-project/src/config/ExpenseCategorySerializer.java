package config;

import entities.ExpenseCategory;

import com.google.gson.Gson;

public class ExpenseCategorySerializer {
    private static final Gson gson = new Gson();

    public static String serialize(ExpenseCategory category) {
        return gson.toJson(category);
    }

    public static ExpenseCategory deserialize(String json) {
        return gson.fromJson(json, ExpenseCategory.class);
    }
}