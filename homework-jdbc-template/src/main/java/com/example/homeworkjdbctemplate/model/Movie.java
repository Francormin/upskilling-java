package com.example.homeworkjdbctemplate.model;

public class Movie {

    private Long id;
    private String title;
    private String director;
    private Integer releaseYear;

    public Movie() {
    }

    public Movie(Long id, String title, String director, Integer releaseYear) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", director='" + director + '\'' +
            ", releaseYear=" + releaseYear +
            '}';
    }

}
