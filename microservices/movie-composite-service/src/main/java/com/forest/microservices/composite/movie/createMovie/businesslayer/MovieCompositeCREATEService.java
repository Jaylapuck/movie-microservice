package com.forest.microservices.composite.movie.createMovie.businesslayer;

import com.forest.api.composite.movie.MovieAggregate;

public interface MovieCompositeCREATEService {

    void createMovie(MovieAggregate model);

}
