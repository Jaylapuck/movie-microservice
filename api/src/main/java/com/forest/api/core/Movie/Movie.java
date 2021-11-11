package com.forest.api.core.Movie;

public class Movie {

    private  int movieId;
    private  String name;
    private  String director;
    private  String theatreReleaseDate;
    private  String serviceAddress;

    public Movie() {
        movieId = 0;
        name = null;
        director = null;
        theatreReleaseDate = null;
        serviceAddress = null;
    }

    public Movie(int movieId, String name, String director, String theatreReleaseDate, String serviceAddress) {
        this.movieId = movieId;
        this.name = name;
        this.director = director;
        this.theatreReleaseDate = theatreReleaseDate;
        this.serviceAddress = serviceAddress;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public String getTheatreReleaseDate() {
        return theatreReleaseDate;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setTheatreReleaseDate(String theatreReleaseDate) {
        this.theatreReleaseDate = theatreReleaseDate;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
