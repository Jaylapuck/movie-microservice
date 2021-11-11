package com.forest.microservices.composite.movie.getMovie.integrationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forest.api.core.Movie.API.MovieServiceGETAPI;
import com.forest.api.core.Movie.Movie;
import com.forest.api.core.Review.API.ReviewServiceGETAPI;
import com.forest.api.core.Review.Review;
import com.forest.utils.exceptions.InvalidInputException;
import com.forest.utils.exceptions.NotFoundException;
import com.forest.utils.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieICompositeGETIntegration implements MovieServiceGETAPI, ReviewServiceGETAPI {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MovieICompositeGETIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String movieServiceUrl;
    private final String reviewServiceUrl;

    public MovieICompositeGETIntegration(
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

    @Override
    public Movie getMovie(int movieId) {
        try {
            String url = movieServiceUrl + "/" + movieId;
            LOGGER.debug("Will call getMovie API on URL: {}", url);
            Movie movie = restTemplate.getForObject(url, Movie.class);
            LOGGER.debug("Found a movie with ID: {}", movieId);
            return movie;
        }
        catch(HttpClientErrorException ex) { //We are the API client, so we need to handle errors {
            throw  handleHttpClientException(ex);
        }
    }

    @Override
    public List<Review> getReview(int movieId) {
        try {
            String url = reviewServiceUrl + "?movieId=" + movieId;
            LOGGER.debug("Will call getReview API on URL: {}", url);
            List<Review> Review = restTemplate.exchange(url,
                    HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<Review>>() {
                    }).getBody();

            LOGGER.debug("Found {} for a movie with ID: {}", Review.size(), movieId);
            return  Review;

        }
        catch (Exception ex) {
            LOGGER.debug("Got an exception while requesting review, return zero review: {}", ex.getMessage());
            return  new ArrayList<>();
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
}
