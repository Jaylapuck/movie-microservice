package com.forest.microservices.core.review.businesslayer;

import com.forest.api.core.Review.Review;
import com.forest.microservices.core.review.datalayer.ReviewEntity;
import com.forest.microservices.core.review.datalayer.ReviewRepository;
import com.forest.utils.exceptions.NumberCannotExceed100Exception;
import com.forest.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements  ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository repository;
    private final ReviewMapper mapper;
    private final ServiceUtil serviceUtil;

    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Review> getReview(int movieId) {
        if (movieId > 100){
            throw new NumberCannotExceed100Exception("Number cannot exceed 100 :" + movieId);
        }
        else {
            List<ReviewEntity> entity = repository.findByMovieId(movieId);
            List<Review> response = mapper.entityListToModelList(entity);
            response.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));
            LOGGER.debug("Review getMovieId: response size: {}", response.size());
            return response;
        }
    }

    @Override
    public Review createReview(Review model) {
        ReviewEntity entity = mapper.modelToEntity(model);
        ReviewEntity newEntity = repository.save(entity);

        LOGGER.debug("reviewService createReview: created a review entity: {}/{}", model.getMovieId(), model.getReviewId());
        return mapper.entitytoModel(newEntity);
    }

    @Override
    public void deleteReview(int movieId) {
        LOGGER.debug("deleteReviews: trying to delete all reviews with movieId: {}", movieId);
        repository.deleteAll(repository.findByMovieId(movieId));
    }
}
