package com.forest.microservices.composite.movie.deleteMovie.businesslayer;

import com.forest.microservices.composite.movie.deleteMovie.integrationlayer.MovieICompositeDELETEIntegration;
import com.forest.microservices.composite.movie.getMovie.businesslayer.MovieCompositeGETServiceImpl;
import com.forest.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class MovieCompositeDELETEServiceImpl  implements MovieCompositeDELETEService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieCompositeGETServiceImpl.class);

    private final MovieICompositeDELETEIntegration integration;

    private final ServiceUtil serviceUtil;

    public MovieCompositeDELETEServiceImpl(MovieICompositeDELETEIntegration integration, ServiceUtil serviceUtil) {
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }
    @Override
    public void deleteMovie(int movieId) {
        LOGGER.debug("deleteCompositeMovie: starting to movie a movie aggregate for movieId: {}", movieId);

        integration.deleteMovie(movieId);
        integration.deleteReviews(movieId);

        LOGGER.debug("deleteCompositeMovie: Deleted a movie aggregate for movieId: {}", movieId);
    }

}
