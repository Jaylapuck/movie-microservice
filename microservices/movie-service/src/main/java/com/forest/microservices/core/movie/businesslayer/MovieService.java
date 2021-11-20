package com.forest.microservices.core.movie.businesslayer;

import com.forest.api.core.Movie.Movie;

import java.util.List;

public interface MovieService {
    public Movie getMoviesById(int movieId);

    public Movie createMovie(Movie model);

    public void deleteMovies(int movieId);

    public Movie updateMovie(int movieId, Movie model);
}
