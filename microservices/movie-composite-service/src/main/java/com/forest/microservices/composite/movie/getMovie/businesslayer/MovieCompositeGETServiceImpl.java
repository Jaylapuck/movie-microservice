package com.forest.microservices.composite.movie.getMovie.businesslayer;

import com.forest.api.composite.movie.MovieAggregate;
import com.forest.api.composite.movie.ReviewSummary;
import com.forest.api.composite.movie.ServiceAddress;
import com.forest.api.core.Movie.Movie;
import com.forest.api.core.Review.Review;
import com.forest.microservices.composite.movie.getMovie.integrationlayer.MovieICompositeGETIntegration;
import com.forest.utils.exceptions.NotFoundException;
import com.forest.utils.http.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCompositeGETServiceImpl implements MovieCompositeGETService {

    private final MovieICompositeGETIntegration integration;

    private final ServiceUtil serviceUtil;

    public MovieCompositeGETServiceImpl(MovieICompositeGETIntegration integration, ServiceUtil serviceUtil) {
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }


    @Override
    public MovieAggregate getMovie(int movieId) {
        Movie movie = integration.getMovie(movieId);
        if (movie == null){ throw new NotFoundException("No movie found for movieId: " + movieId); }

        List<Review> review = integration.getReview(movieId);

        return createMovieAggregate(movie, review, serviceUtil.getServiceAddress());
    }

    private MovieAggregate createMovieAggregate(Movie movie , List<Review> review, String serviceAddress) {

        //1. Setup the movie info
        int movieId = movie.getMovieId();
        String name = movie.getName();
        String director = movie.getDirector();
        String date = movie.getTheatreReleaseDate();

        //2. Copy summary review if, if available
        List<ReviewSummary> reviewSummaries = (review == null) ? null :
                review.stream().map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(),
                        r.getSubject(), r.getContent())).collect(Collectors.toList());

        //3. Create summary of Microservice addresses

        String movieAddress = movie.getServiceAddress();
        String reviewAddress = (review !=
                null && review.size() > 0) ? review.get(0).getServiceAddress() : "";
        ServiceAddress serviceAddresses  = new ServiceAddress(serviceAddress, movieAddress, reviewAddress);

        //5. return
        return new MovieAggregate(movieId, name, director, date, reviewSummaries, serviceAddresses);
    }

}
