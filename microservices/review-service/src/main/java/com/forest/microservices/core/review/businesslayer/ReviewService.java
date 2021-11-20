package com.forest.microservices.core.review.businesslayer;


import com.forest.api.core.Review.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReview(int movieId);

    Review createReview(Review model);

    void deleteReview(int movieId);

    Review updateReview(int movieId, Review model);
}
