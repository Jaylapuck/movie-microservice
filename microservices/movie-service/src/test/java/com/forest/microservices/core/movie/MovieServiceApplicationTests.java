package com.forest.microservices.core.movie;

import com.forest.api.core.Movie.Movie;
import com.forest.microservices.core.movie.datalayer.MovieEntity;
import com.forest.microservices.core.movie.datalayer.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:movie-db"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class MovieServiceApplicationTests {

	private static final int MOVIE_ID_OKAY = 1;

	@Autowired
	private WebTestClient client;

	@Autowired
	private MovieRepository repository;

	@BeforeEach
	public void setupDb(){
		repository.deleteAll();
	}

	@Test
	public void  getMovieById(){

		//add the product entity to the db
		MovieEntity entity = new MovieEntity(MOVIE_ID_OKAY, "name-", "director-", "03-12-2005");
		repository.save(entity);

		client.get()
				.uri("/movie/" + MOVIE_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.movieId").isEqualTo(MOVIE_ID_OKAY);
	}

	@Test
	public void createMovie(){

		//create an product model
		Movie model = new Movie(MOVIE_ID_OKAY, "name-", "director-", "03-12-2005","SA");

		//send the post request
		client.post()
				.uri("/movie")
				.body(just(model), Movie.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.movieId").isEqualTo(MOVIE_ID_OKAY);


	}

	@Test
	public void deleteMovie(){
		MovieEntity entity = new MovieEntity(MOVIE_ID_OKAY, "name-", "director-", "03-12-2005");
		repository.save(entity);

		client.delete()
				.uri("/movie/" + MOVIE_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

	}

	@Test
	public void getMovieIdInvalidParameterString(){

		final String MOVIE_ID_INVALID_STRING = "not-integer";
		client.get()
				.uri("/movie/" + MOVIE_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie/" + MOVIE_ID_INVALID_STRING)
				.jsonPath("$.message").isEqualTo("Type mismatch.");

	}

	@Test
	public void getMovieIdMovieNotFound(){

		final int MOVIE_NOT_FOUND = 13;
		client.get()
				.uri("/movie/" + MOVIE_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie/" + MOVIE_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("No movie found for movieId:" + MOVIE_NOT_FOUND);

	}

	@Test
	public void getMovieIdMovieInvalidNegativeNumber(){

		final int MOVIE_ID_INVALID_NEGATIVE_VALUE = -1;
		client.get()
				.uri("/movie/" + MOVIE_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie/" + MOVIE_ID_INVALID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("invalid movieId: " + MOVIE_ID_INVALID_NEGATIVE_VALUE);

	}

	@Test
	public void getMovieIdMovieOver100(){
		final int MOVIE_ID_OVER_HUNDRED = 150;
		client.get()
				.uri("/movie/" + MOVIE_ID_OVER_HUNDRED)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/movie/" + MOVIE_ID_OVER_HUNDRED)
				.jsonPath("$.message").isEqualTo("The movieId cannot exceed 100 :" + MOVIE_ID_OVER_HUNDRED);

	}

	@Test
	void contextLoads() {
	}

}
