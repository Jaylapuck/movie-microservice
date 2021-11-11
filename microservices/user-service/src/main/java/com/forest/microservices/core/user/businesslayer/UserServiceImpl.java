package com.forest.microservices.core.user.businesslayer;

import com.forest.api.core.User.User;
import com.forest.microservices.core.user.datalayer.UserEntity;
import com.forest.microservices.core.user.datalayer.UserRepository;
import com.forest.utils.exceptions.InvalidInputException;
import com.forest.utils.exceptions.NotFoundException;
import com.forest.utils.http.ServiceUtil;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    private final UserMapper mapper;

    private final ServiceUtil serviceUtil;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public User getUserById(int userId) {
        UserEntity entity = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("No user found for userId:" + userId));
        User response = mapper.entityToModel(entity);
        response.setServiceAddress(serviceUtil.getServiceAddress());
        LOGGER.debug("User getUserById: found userId: {}", response.getUserId());
        return response;
    }

    @Override
    public User createUser(User model) {
        try {
            UserEntity entity = mapper.modelToEntity(model);
            UserEntity newEntity = repository.save(entity);
            LOGGER.debug("createUser: entity created for userId: {}", model.getUserId());

            return mapper.entityToModel(newEntity);
        }
        catch (DuplicateKeyException e){
            throw new InvalidInputException("Duplicate key for userId " + model.getUserId());
        }
    }

    @Override
    public void deleteUser(int userId) {
        LOGGER.debug("deleteUser: trying to delete entity with userId: {}", userId);
        repository.findByUserId(userId).ifPresent(repository::delete);
    }
}
