package com.forest.api.core.Review.API;

import com.forest.api.core.Movie.Movie;
import com.forest.api.core.Review.Review;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviewServiceUPDATEAPI {

   @PutMapping(
            value = "/review/{reviewId}",
            consumes = "application/json",
            produces = "application/json")
   Review updateReview(@PathVariable int reviewId, @RequestBody Review model);
}
