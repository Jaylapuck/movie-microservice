package com.forest.api.composite.movie.API;

import com.forest.api.composite.movie.MovieAggregate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MovieCompositeCREATEServiceAPI {

    @PostMapping(
            value = "/movie-composite",
            consumes = "application/json"
    )
    void createCompositeMovie(@RequestBody MovieAggregate model);
}
