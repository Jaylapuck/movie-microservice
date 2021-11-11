package com.forest.microservices.core.movie.datalayer;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private  int movieId;
    private  String name;
    private  String director;
    private  String theatreReleaseDate;

    public MovieEntity(int movieId, String name, String director, String theatreReleaseDate) {
        this.movieId = movieId;
        this.name = name;
        this.director = director;
        this.theatreReleaseDate = theatreReleaseDate;
    }

    public MovieEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTheatreReleaseDate() {
        return theatreReleaseDate;
    }

    public void setTheatreReleaseDate(String theatreReleaseDate) {
        this.theatreReleaseDate = theatreReleaseDate;
    }
}
