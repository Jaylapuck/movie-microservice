package com.forest.api.core.Review;


public class Review {
    private  int movieId;
    private   int reviewId;
    private  String author;
    private  String subject;
    private  String content;
    private  String serviceAddress;

    public Review(int movieId, int reviewId, String author, String subject, String content, String serviceAddress) {
        this.movieId = movieId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public Review() {
        movieId = 0;
        reviewId = 0;
        author =null;
        subject=null;
        content = null;
        serviceAddress = null;
    }

    public int getMovieId() {
        return this.movieId;
    }

    public int getReviewId() {
        return this.reviewId;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getContent() {
        return this.content;
    }

    public String getServiceAddress() {
        return this.serviceAddress;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
