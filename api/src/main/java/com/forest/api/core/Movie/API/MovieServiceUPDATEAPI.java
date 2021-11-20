package com.forest.api.core.Movie.API;

import com.forest.api.core.Movie.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MovieServiceUPDATEAPI {

     @PutMapping(
        value = "/movie/{movieId}",
        consumes = "application/json",
        produces = "application/json"
    )
    Movie updateMovie(@PathVariable int movieId, @RequestBody Movie movie);
}
