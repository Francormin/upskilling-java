package com.example.homeworkjdbctemplate.constants;

public class MovieSqlQueries {

    public static final String GET_ALL = "SELECT * FROM movies";
    public static final String GET_BY_ID = "SELECT * FROM movies WHERE id = ?";
    public static final String GET_BY_TITLE = "SELECT * FROM movies WHERE title LIKE '%' || ? || '%'";
    public static final String CREATE = "INSERT INTO movies (title, director, release_year) VALUES (?, ?, ?)";
    public static final String UPDATE = "UPDATE movies SET title = ?, director = ?, release_year = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM movies WHERE id = ?";

}
