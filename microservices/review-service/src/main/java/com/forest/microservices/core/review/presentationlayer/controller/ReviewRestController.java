package com.forest.microservices.core.review.presentationlayer.controller;

import com.forest.api.core.Review.API.ReviewServiceCREATEAPI;
import com.forest.api.core.Review.API.ReviewServiceDELETEAPI;
import com.forest.api.core.Review.API.ReviewServiceGETAPI;
import com.forest.api.core.Review.Review;
import com.forest.microservices.core.review.businesslayer.ReviewService;
import com.forest.utils.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewRestController implements ReviewServiceCREATEAPI, ReviewServiceGETAPI, ReviewServiceDELETEAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRestController.class);

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public List<Review> getReview(int movieId) {

        if (movieId < 1) throw new InvalidInputException("Invalid movieId: " + movieId);

        /*
        if (movieId == 47) {
            LOGGER.debug("No review found for movieId: {}", movieId);
            return new ArrayList<>();
        }
        */

        List<Review> listReview = reviewService.getReview(movieId);
        /*
        listReview.add(new Review(movieId, 1, "Author 1", "Subject 1", "Content 1", serviceUtil.getServiceAddress()));
        listReview.add(new Review(movieId, 2, "Author 2", "Subject 2", "Content 2", serviceUtil.getServiceAddress()));
        listReview.add(new Review(movieId, 3, "Author 3", "Subject 3", "Content 3", serviceUtil.getServiceAddress()));

         */

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
}
