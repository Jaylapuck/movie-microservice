package com.forest.microservices.core.movie.businesslayer;

import com.forest.api.core.Movie.Movie;
import com.forest.microservices.core.movie.datalayer.MovieEntity;
import com.forest.microservices.core.movie.datalayer.MovieRepository;
import com.forest.utils.exceptions.InvalidInputException;
import com.forest.utils.exceptions.NotFoundException;
import com.forest.utils.exceptions.NumberCannotExceed100Exception;
import com.forest.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository repository;
    private final MovieMapper mapper;
    private final ServiceUtil serviceUtil;

    public MovieServiceImpl(MovieRepository repository, MovieMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Movie getMoviesById(int movieId) {

        if(movieId < 1) throw new InvalidInputException("invalid movieId: " + movieId);
        MovieEntity entity = repository.findByMovieId(movieId);

        if (entity == null) {
            throw new NotFoundException("No movie found for movieId:" + movieId);
        } else {
            Movie response = mapper.entitytoModel(entity);
            response.setServiceAddress(serviceUtil.getServiceAddress());
            LOGGER.debug("Movie getMovies: found movieId: {}", response.getMovieId());
            return response;
        }
    }

    @Override
    public Movie createMovie(Movie model) {
            MovieEntity entity = mapper.modelToEntity(model);
            MovieEntity newEntity = repository.save(entity);
            LOGGER.debug("movieService createMovie: created a movie entity: {}", model.getMovieId());
            return mapper.entitytoModel(newEntity);
    }


    @Override
    public void deleteMovies(int movieId) {
        LOGGER.debug("deleteMovies: trying to delete a movie with productId: {}", movieId);
        if (repository.findByMovieId(movieId) != null){
            repository.delete(repository.findByMovieId(movieId));
        }
    }

    @Override
    public Movie updateMovie(int movieId, Movie model) {
        LOGGER.debug("updateMovie: trying to update a movie with productId: {}", movieId);
        MovieEntity entity = repository.findByMovieId(movieId);
        if (entity == null) {
            throw new NotFoundException("No movie found for movieId:" + movieId);
        } else {
            MovieEntity newEntity = mapper.modelToEntity(model);
            newEntity.setMovieId(movieId);
            MovieEntity updatedEntity = repository.save(newEntity);
            LOGGER.debug("updateMovie: updated a movie entity: {}", movieId);
            return mapper.entitytoModel(updatedEntity);
        }
    }
}
