package com.forest.api.core.Movie.API;

import com.forest.api.core.Movie.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface MovieServiceGETAPI {

    @GetMapping(
            value = "/movie/{movieId}",
            produces = "application/json"
    )
    Movie getMovie(@PathVariable int movieId);
}
