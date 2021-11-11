package com.forest.api.composite.movie;

public class ServiceAddress {
    private  String  compositeAddress;
    private  String  movieAddress;
    private  String  reviewAddress;

    public ServiceAddress(String compositeAddress, String movieAddress, String reviewAddress) {
        this.compositeAddress = compositeAddress;
        this.movieAddress = movieAddress;
        this.reviewAddress = reviewAddress;
    }

    public ServiceAddress() {
    }

    public String getCompositeAddress() {
        return compositeAddress;
    }

    public String getMovieAddress() {
        return movieAddress;
    }

    public String getReviewAddress() {
        return reviewAddress;
    }

    public void setCompositeAddress(String compositeAddress) {
        this.compositeAddress = compositeAddress;
    }

    public void setMovieAddress(String movieAddress) {
        this.movieAddress = movieAddress;
    }

    public void setReviewAddress(String reviewAddress) {
        this.reviewAddress = reviewAddress;
    }
}

