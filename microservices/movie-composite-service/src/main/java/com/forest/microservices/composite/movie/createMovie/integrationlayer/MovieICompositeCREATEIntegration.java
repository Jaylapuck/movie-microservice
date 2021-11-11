package com.forest.microservices.composite.movie.createMovie.integrationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forest.api.core.Movie.API.MovieServiceCREATEAPI;
import com.forest.api.core.Movie.Movie;
import com.forest.api.core.Review.API.ReviewServiceCREATEAPI;
import com.forest.api.core.Review.Review;
import com.forest.microservices.composite.movie.getMovie.integrationlayer.MovieICompositeGETIntegration;
import com.forest.utils.exceptions.InvalidInputException;
import com.forest.utils.exceptions.NotFoundException;
import com.forest.utils.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class MovieICompositeCREATEIntegration implements MovieServiceCREATEAPI, ReviewServiceCREATEAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieICompositeGETIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String movieServiceUrl;
    private final String reviewServiceUrl;

    public MovieICompositeCREATEIntegration (
            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.movie-service.host}") String movieServiceHost,
            @Value("${app.movie-service.port}") int movieServicePort,

            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") int reviewServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        movieServiceUrl = "http://" + movieServiceHost + ":" + movieServicePort + "/movie";
        reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review";
    }

    private  RuntimeException handleHttpClientException(HttpClientErrorException ex){
        switch (ex.getStatusCode()){
            case NOT_FOUND:
                throw new NotFoundException(getErrorMessage(ex));
            case UNPROCESSABLE_ENTITY:
                throw new InvalidInputException(getErrorMessage(ex));
            default:
                LOGGER.warn("Got an unexpected HTTP error: {}, will rethrow it.", ex.getStatusCode());
                LOGGER.warn("Error body: {} ", ex.getResponseBodyAsString());
                throw ex;
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch(IOException ioException) {
            return ioException.getMessage();
        }
    }

    @Override
    public Movie createMovie(Movie model) {
        try {
            String url = movieServiceUrl;
            LOGGER.debug("Will call createMovie API on url {}", url);
            return restTemplate.postForObject(movieServiceUrl, model, Movie.class);
        }
        catch (HttpClientErrorException ex){
            throw  handleHttpClientException(ex);
        }
    }

    @Override
    public Review createReview(Review model) {
        try {
            LOGGER.debug("Will call createReview API on URL: {}", reviewServiceUrl);
            return restTemplate.postForObject(reviewServiceUrl, model, Review.class);
        }
        catch (HttpClientErrorException ex){
            throw  handleHttpClientException(ex);
        }
    }
}
