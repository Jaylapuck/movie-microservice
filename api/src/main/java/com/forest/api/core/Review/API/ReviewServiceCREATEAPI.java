package com.forest.api.core.Review.API;

import com.forest.api.core.Review.Review;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviewServiceCREATEAPI {

    @PostMapping(
            value = "/review",
            consumes = "application/json",
            produces = "application/json")
    Review createReview(@RequestBody Review model);
}
