package com.forest.microservices.composite.movie.createMovie.businesslayer;

import com.forest.api.composite.movie.MovieAggregate;
import com.forest.api.core.Movie.Movie;
import com.forest.api.core.Review.Review;
import com.forest.microservices.composite.movie.createMovie.integrationlayer.MovieICompositeCREATEIntegration;
import com.forest.microservices.composite.movie.getMovie.businesslayer.MovieCompositeGETServiceImpl;
import com.forest.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieCompositeCREATEServiceImpl  implements MovieCompositeCREATEService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieCompositeGETServiceImpl.class);

    private final MovieICompositeCREATEIntegration integration;

    private final ServiceUtil serviceUtil;

    public MovieCompositeCREATEServiceImpl(MovieICompositeCREATEIntegration integration, ServiceUtil serviceUtil) {
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public void createMovie(MovieAggregate model) {

        try {
            LOGGER.debug("createCompositeMovie: creates a new composite entity for movieId: {}", model.getMovieId());
            Movie movie = new Movie(model.getMovieId(), model.getName(), model.getDirector(),model.getTheatreReleaseDate(), null);
            integration.createMovie(movie);

            if (model.getReview() != null){
                model.getReview().forEach(r -> {
                    Review review = new Review(model.getMovieId(), r.getReviewId(),
                            r.getAuthor(), r.getSubject(), r.getContent(), null);
                    integration.createReview(review);
                });

            }
            LOGGER.debug("createCompositeMovie: composite entities created for movieId: {}", model.getMovieId());
        } catch (RuntimeException rte) {
            LOGGER.warn("createCompositeMovie failed", rte);
        }
    }
}
