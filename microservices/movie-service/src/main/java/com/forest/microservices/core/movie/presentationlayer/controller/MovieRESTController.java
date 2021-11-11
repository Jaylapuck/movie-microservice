package com.forest.microservices.core.movie.presentationlayer.controller;

import com.forest.api.core.Movie.API.MovieServiceCREATEAPI;
import com.forest.api.core.Movie.API.MovieServiceDELETEAPI;
import com.forest.api.core.Movie.API.MovieServiceGETAPI;
import com.forest.api.core.Movie.Movie;
import com.forest.microservices.core.movie.businesslayer.MovieService;
import com.forest.utils.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieRESTController implements MovieServiceGETAPI, MovieServiceCREATEAPI, MovieServiceDELETEAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRESTController.class);

    private final MovieService movieService;

    public MovieRESTController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Movie getMovie(int movieId) {
        LOGGER.debug("/movie MS return the found movie fort movieId: " + movieId);

        if(movieId < 1) throw new InvalidInputException("invalid movieId: " + movieId);

        //if(movieId == 13) throw new NotFoundException("No movie found for movieId:" + movieId);

        //if(movieId > 100) throw new NumberCannotExceed100Exception("The movieId cannot exceed 100 :"+ movieId);

        return movieService.getMoviesById(movieId);
    }

    @Override
    public Movie createMovie(Movie model) {
        Movie movie = movieService.createMovie(model);
        LOGGER.debug("REST createMovie: request sent to movie service for movieId: {} ", movie.getMovieId());
        return movie;
    }

    @Override
    public void deleteMovie(int movieId) {
        LOGGER.debug("REST deleteMovie: tried to delete movieId: {} ", movieId);
        movieService.deleteMovies(movieId);
    }
}
