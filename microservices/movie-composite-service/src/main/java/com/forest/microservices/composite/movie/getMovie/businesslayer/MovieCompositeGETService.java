package com.forest.microservices.composite.movie.getMovie.businesslayer;

import com.forest.api.composite.movie.MovieAggregate;

public interface MovieCompositeGETService {

    MovieAggregate getMovie(int movieId);

}
