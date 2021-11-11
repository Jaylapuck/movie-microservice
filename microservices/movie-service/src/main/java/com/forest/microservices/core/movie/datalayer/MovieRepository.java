package com.forest.microservices.core.movie.datalayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {

    @Transactional(readOnly = true)
    MovieEntity findByMovieId(int movieId);
}
