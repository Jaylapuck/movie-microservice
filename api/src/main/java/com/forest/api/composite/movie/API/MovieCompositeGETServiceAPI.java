package com.forest.api.composite.movie.API;

import com.forest.api.composite.movie.MovieAggregate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface MovieCompositeGETServiceAPI {

    @GetMapping(
            value = "/movie-composite/{movieId}",
            produces = "application/json"
    )
    MovieAggregate getMovieComposite(@PathVariable int movieId);
}
