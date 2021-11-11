package com.forest.microservices.core.review.datalayer;

import javax.persistence.*;

@Entity
@Table(name = "reviews", indexes = { @Index(name = "reviews_unique_idx",
        unique = true, columnList= "movieId,reviewId") })
public class ReviewEntity {
    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private int movieId;
    private int reviewId;
    private String author;
    private String subject;
    private String content;

    public ReviewEntity(int movieId, int reviewId, String author, String subject, String content) {
        this.movieId = movieId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }

    public ReviewEntity() {
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

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

