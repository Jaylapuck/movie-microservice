package com.forest.api.core.Review.API;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewServiceDELETEAPI {

    @DeleteMapping(
            value = "/review"
    )
    void deleteReviews(@RequestParam(value = "movieId", required = true) int movieId);
}
