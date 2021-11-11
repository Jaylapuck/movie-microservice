package com.forest.api.composite.movie.API;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface MovieCompositeDELETEServiceAPI {

    @DeleteMapping(
            value = "/movie-composite/{movieId}",
            produces = "application/json"
    )
    void deleteCompositeMovie(@PathVariable int movieId);

}
