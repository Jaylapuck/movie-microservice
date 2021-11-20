package com.forest.microservices.core.review.presentationlayer.controller;

import com.forest.api.core.Review.API.ReviewServiceCREATEAPI;
import com.forest.api.core.Review.API.ReviewServiceDELETEAPI;
import com.forest.api.core.Review.API.ReviewServiceGETAPI;
import com.forest.api.core.Review.API.ReviewServiceUPDATEAPI;
import com.forest.api.core.Review.Review;
import com.forest.microservices.core.review.businesslayer.ReviewService;
import com.forest.utils.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewRestController implements ReviewServiceCREATEAPI, ReviewServiceGETAPI, ReviewServiceDELETEAPI, ReviewServiceUPDATEAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRestController.class);

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public List<Review> getReview(int movieId) {

        List<Review> listReview = reviewService.getReview(movieId);

        LOGGER.debug("/reviews found response size: {}", listReview.size());

        return listReview;
    }

    @Override
    public Review createReview(Review model) {
        Review review = reviewService.createReview(model);
        LOGGER.debug("REST Controller createReview: created a review entity: {}{}", review.getMovieId(), review.getReviewId());
        return  review;
    }

    @Override
    public void deleteReviews(int movieId) {
        LOGGER.debug("REST Controller deleteReviews: tried to delete all entity: {}", movieId);
        reviewService.deleteReview(movieId);
    }

    @Override
    public Review updateReview(int movieId, Review model) {
        Review review = reviewService.updateReview(movieId, model);
        LOGGER.debug("REST Controller updateReview: updated a review entity: {}{}", review.getMovieId(), review.getReviewId());
        return review;
    }
}
