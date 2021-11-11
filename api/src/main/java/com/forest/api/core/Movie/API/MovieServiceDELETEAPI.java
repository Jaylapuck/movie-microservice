package com.forest.api.core.Movie.API;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface MovieServiceDELETEAPI {

    @DeleteMapping(
            value = "/movie/{movieId}"
    )
    void deleteMovie(@PathVariable int movieId);
}
