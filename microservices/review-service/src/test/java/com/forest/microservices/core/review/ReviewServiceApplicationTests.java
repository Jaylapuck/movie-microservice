package com.forest.microservices.core.review;

import com.forest.api.core.Review.Review;
import com.forest.microservices.core.review.datalayer.ReviewEntity;
import com.forest.microservices.core.review.datalayer.ReviewRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:review-db"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class ReviewServiceApplicationTests {

	private static final int MOVIE_ID_OKAY = 1;
	private static final int REVIEW_IS_OKAY =1;

	@Autowired
	private WebTestClient client;

	@Autowired
	private ReviewRepository repository;

	@BeforeEach
	public void setupDb(){
		repository.deleteAll();
	}

	@Test
	public void getReviewMissingParameter() {

		int expectedLength = 3;
		client.get()
				.uri("/review")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Required int parameter 'movieId' is not present");


	}

	@Test
	public void getMovieIdInvalidNegativeNumber(){

		final int MOVIE_ID_INVALID_NEGATIVE_VALUE = -1;
		client.get()
				.uri("/review?movieId=" + MOVIE_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Invalid movieId: " + MOVIE_ID_INVALID_NEGATIVE_VALUE);

	}

	@Test
	public void getReviewInvalidString() {

		final String MOVIE_ID_INVALID_STRING = "not-integer";
		int expectedLength = 3;
		client.get()
				.uri("/review?movieId=" + MOVIE_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}

	@Test
	public void getReviewMovieIdNotFound() {

		final int MOVIE_NOT_FOUND = 47;
		int expectedLength = 0;
		client.get()
				.uri("/review?movieId=" + MOVIE_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);
	}

	@Test
	public void getReviewInvalidId() {

		final int MOVIE_ID_INVALID_NEGATIVE_VALUE =-1;
		client.get()
				.uri("/review?movieId=" + MOVIE_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Invalid movieId: " + MOVIE_ID_INVALID_NEGATIVE_VALUE);
	}

	@Test
	public void getReviewByMovieId() {

		ReviewEntity entity1 = new ReviewEntity(MOVIE_ID_OKAY, 1,"author1", "subject1", "content1");
		repository.save(entity1);
		ReviewEntity entity2 = new ReviewEntity(MOVIE_ID_OKAY, 2,"author2", "subject2", "content2");
		repository.save(entity2);
		ReviewEntity entity3 = new ReviewEntity(MOVIE_ID_OKAY, 3,"author3", "subject3", "content3");
		repository.save(entity3);

		assertEquals(3, repository.findByMovieId(MOVIE_ID_OKAY).size());

		int expectedLength = 3;
		client.get()
				.uri("/review?movieId=" + MOVIE_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength)
				.jsonPath("$[0].movieId").isEqualTo(MOVIE_ID_OKAY)
				.jsonPath("$[1].movieId").isEqualTo(MOVIE_ID_OKAY)
				.jsonPath("$[2].movieId").isEqualTo(MOVIE_ID_OKAY);

	}


	@Test
	public void createReview(){

		//create an review model
		Review model = new Review(MOVIE_ID_OKAY, 1,"author1", "subject1", "content1", "SA");

		//send the post request
		client.post()
				.uri("/review")
				.body(just(model), Review.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.movieId").isEqualTo(MOVIE_ID_OKAY);

		assertEquals(1, repository.findByMovieId(MOVIE_ID_OKAY).size());
	}



	@Test
	public void deleteReview(){
		ReviewEntity entity = new ReviewEntity(MOVIE_ID_OKAY, REVIEW_IS_OKAY , "author1", "subject1", "content1");
		repository.save(entity);

		assertEquals(1, repository.findByMovieId(MOVIE_ID_OKAY).size());

		client.delete()
				.uri("/review?movieId=" + MOVIE_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		assertEquals(0, repository.findByMovieId(MOVIE_ID_OKAY).size());
	}

	@Test
	void contextLoads() {
	}
}