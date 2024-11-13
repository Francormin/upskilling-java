package com.example.homeworkjdbctemplate.dto;

public class MovieDto {

    private String title;
    private String director;
    private Integer releaseYear;

    public MovieDto() {
    }

    public MovieDto(String title, String director, Integer releaseYear) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
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
        return "MovieRequest{" +
            "title='" + title + '\'' +
            ", director='" + director + '\'' +
            ", releaseYear=" + releaseYear +
            '}';
    }

}
