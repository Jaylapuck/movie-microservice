package com.forest.api.composite.movie;

import java.util.List;

public class MovieAggregate {
    private int movieId;
    private String name;
    private String director;
    private String theatreReleaseDate;
    private List<ReviewSummary> review;
    private ServiceAddress serviceAddress;

    public MovieAggregate(int movieId, String name, String director, String theatreReleaseDate, List<ReviewSummary> review, ServiceAddress serviceAddress) {
        this.movieId = movieId;
        this.name = name;
        this.director = director;
        this.theatreReleaseDate = theatreReleaseDate;
        this.review = review;
        this.serviceAddress = serviceAddress;
    }

    public MovieAggregate() {
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

    public List<ReviewSummary> getReview() {
        return review;
    }

    public ServiceAddress getServiceAddress() {
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

    public void setReview(List<ReviewSummary> review) {
        this.review = review;
    }

    public void setServiceAddress(ServiceAddress serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
