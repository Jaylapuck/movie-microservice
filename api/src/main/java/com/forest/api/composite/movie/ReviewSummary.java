package com.forest.api.composite.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ReviewSummary {
    private  int reviewId;
    private String author;
    private  String subject;
    private String content;

    public ReviewSummary(int reviewId, String author, String subject, String content) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
