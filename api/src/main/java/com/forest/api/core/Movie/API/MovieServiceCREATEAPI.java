package com.forest.api.core.Movie.API;

import com.forest.api.core.Movie.Movie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MovieServiceCREATEAPI {

    @PostMapping(
            value = "/movie",
            consumes = "application/json",
            produces = "application/json")
    Movie createMovie(@RequestBody Movie model);
}
