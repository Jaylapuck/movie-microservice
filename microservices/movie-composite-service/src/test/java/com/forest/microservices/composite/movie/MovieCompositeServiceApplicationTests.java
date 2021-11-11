package com.forest.microservices.composite.movie;

import com.forest.api.composite.movie.MovieAggregate;
import com.forest.api.composite.movie.ReviewSummary;
import com.forest.api.core.Movie.Movie;
import com.forest.api.core.Review.Review;
import com.forest.microservices.composite.movie.createMovie.integrationlayer.MovieICompositeCREATEIntegration;
import com.forest.microservices.composite.movie.deleteMovie.integrationlayer.MovieICompositeDELETEIntegration;
import com.forest.microservices.composite.movie.getMovie.integrationlayer.MovieICompositeGETIntegration;
import com.forest.utils.exceptions.InvalidInputException;
import com.forest.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class MovieCompositeServiceApplicationTests {

	private static final int MOVIE_ID_OKAY = 1;
	private static final int MOVIE_NOT_FOUND = 98 ;
	private static final String MOVIE_ID_INVALID_STRING = "not-integer";
	private static final int MOVIE_ID_INVALID_NEGATIVE_VALUE = -1;
	private static final int MOVIE_ID_OVER_HUNDRED = 150;

	@Autowired
	private WebTestClient client;

	@MockBean
	private MovieICompositeGETIntegration compositeGETIntegration;

	@MockBean
	private MovieICompositeCREATEIntegration compositeCREATEIntegration;

	@MockBean
	private MovieICompositeDELETEIntegration compositeDELETEIntegration;

	@BeforeEach
	void setup(){
		when(compositeGETIntegration.getMovie(MOVIE_ID_OKAY)).
				thenReturn(new Movie(MOVIE_ID_OKAY, "name", "director 1" , "date 1","mock-address"));

		when(compositeGETIntegration.getReview(MOVIE_ID_OKAY)).
				thenReturn(Collections.singletonList(new Review(MOVIE_ID_OKAY, 1, "author 1", "subject 1", "date 1", "mock-address")));

		when(compositeGETIntegration.getMovie(MOVIE_NOT_FOUND)).
				thenThrow(new NotFoundException("NOT FOUND: " + MOVIE_NOT_FOUND));

		when(compositeGETIntegration.getMovie(MOVIE_ID_INVALID_NEGATIVE_VALUE)).
				thenThrow(new InvalidInputException("INVALID INPUT: " + MOVIE_ID_INVALID_NEGATIVE_VALUE));

		when(compositeGETIntegration.getMovie(MOVIE_ID_OVER_HUNDRED)).
				thenThrow(new InvalidInputException("Number exceeded over 100: " + MOVIE_ID_OVER_HUNDRED));
	}

	@Test
	public void getMovieById(){
		client.get()
				.uri("/movie-composite/" + MOVIE_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.movieId").isEqualTo(MOVIE_ID_OKAY)
				.jsonPath("$.review.length()").isEqualTo(1);
	}

	@Test
	public void getMovieIdIsOver100(){

		client.get()
				.uri("/movie-composite/" + MOVIE_ID_OVER_HUNDRED)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie-composite/" + MOVIE_ID_OVER_HUNDRED)
				.jsonPath("$.message").isEqualTo("Number exceeded over 100: " + MOVIE_ID_OVER_HUNDRED);

	}
	@Test
	public void getMovieNotFound(){
		client.get()
				.uri("/movie-composite/" + MOVIE_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie-composite/" + MOVIE_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: " + MOVIE_NOT_FOUND);

	}
	@Test
	public void getMovieInvalidInput(){
		client.get()
				.uri("/movie-composite/" + MOVIE_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie-composite/" + MOVIE_ID_INVALID_STRING)
				.jsonPath("$.message").isEqualTo("Type mismatch.");

	}

	@Test
	public void getMovieNegativeValue(){
		client.get()
				.uri("/movie-composite/" + MOVIE_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie-composite/" + MOVIE_ID_INVALID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("INVALID INPUT: " + MOVIE_ID_INVALID_NEGATIVE_VALUE);

	}

	@Test
	public void createCompositeMovieNoReviews(){

		MovieAggregate compositeMovie = new MovieAggregate(MOVIE_ID_OKAY, "name 1", "director 1", "date 1", null, null);

		client.post()
				.uri("/movie-composite/")
				.body(just(compositeMovie),MovieAggregate.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();

	}

	@Test
	public void createCompositeMovieOneOneReview(){

		MovieAggregate compositeMovie = new MovieAggregate(MOVIE_ID_OKAY, "name 1", "director 1", "date 1",
				Collections.singletonList(new ReviewSummary(1, "a", "s", "c" )), null);

		client.post()
				.uri("/movie-composite/")
				.body(just(compositeMovie),MovieAggregate.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void deleteCompositeMovie(){
		MovieAggregate compositeMovie = new MovieAggregate(MOVIE_ID_OKAY, "name 1", "director 1","data 1",
				Collections.singletonList(new ReviewSummary(1, "a", "s", "c")),
				null);

		client.post()
				.uri("/movie-composite/")
				.body(just(compositeMovie),MovieAggregate.class)
				.exchange()
				.expectStatus().isOk();

		client.delete()
				.uri("/movie-composite/" + compositeMovie.getMovieId())
				.exchange()
				.expectStatus().isOk();

		client.delete()
				.uri("/movie-composite/" + compositeMovie.getMovieId())
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void contextLoads() {
	}

}
