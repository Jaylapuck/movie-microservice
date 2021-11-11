package com.forest.microservices.composite.movie.presentationlayer.controller;

import com.forest.api.composite.movie.API.MovieCompositeCREATEServiceAPI;
import com.forest.api.composite.movie.API.MovieCompositeDELETEServiceAPI;
import com.forest.api.composite.movie.API.MovieCompositeGETServiceAPI;
import com.forest.api.composite.movie.MovieAggregate;
import com.forest.microservices.composite.movie.createMovie.businesslayer.MovieCompositeCREATEService;
import com.forest.microservices.composite.movie.deleteMovie.businesslayer.MovieCompositeDELETEService;
import com.forest.microservices.composite.movie.getMovie.businesslayer.MovieCompositeGETService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieCompositeRestController implements MovieCompositeCREATEServiceAPI, MovieCompositeGETServiceAPI, MovieCompositeDELETEServiceAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieCompositeRestController.class);
    private final MovieCompositeCREATEService movieCompositeCREATEService;
    private final MovieCompositeGETService movieCompositeGETService;
    private final MovieCompositeDELETEService movieCompositeDELETEService;

    public MovieCompositeRestController(MovieCompositeCREATEService movieCompositeCREATEService, MovieCompositeGETService movieCompositeGETService, MovieCompositeDELETEService movieCompositeDELETEService) {
        this.movieCompositeCREATEService = movieCompositeCREATEService;
        this.movieCompositeGETService = movieCompositeGETService;
        this.movieCompositeDELETEService = movieCompositeDELETEService;
    }

    @Override
    public void createCompositeMovie(MovieAggregate model) {
        LOGGER.debug("MovieComposite REST received createCompositeMovie request for movieId");
        movieCompositeCREATEService.createMovie(model);
    }

    @Override
    public void deleteCompositeMovie(int movieId) {
        LOGGER.debug("MovieComposite REST received deleteCompositeMovie request for movieId: {}",movieId);
        movieCompositeDELETEService.deleteMovie(movieId);
    }

    @Override
    public MovieAggregate getMovieComposite(int movieId) {
        LOGGER.debug("MovieComposite REST received getMovieComposite request for movieId: {}",movieId);
        return movieCompositeGETService.getMovie(movieId);
    }
}
