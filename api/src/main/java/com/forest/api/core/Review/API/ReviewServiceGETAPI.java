package com.forest.api.core.Review.API;

import com.forest.api.core.Review.Review;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewServiceGETAPI {

    @GetMapping(
            value = "/review",
            produces = "application/json"
    )
    List<Review> getReview(@RequestParam(value = "movieId", required = true) int movieId);
}
