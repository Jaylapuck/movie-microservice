package com.forest.api.core.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Review {
    private  int movieId;
    private   int reviewId;
    private  String author;
    private  String subject;
    private  String content;
    private  String serviceAddress;

    public Review(int movieId, int reviewId, String author, String subject, String content, Object o) {
    }
}
