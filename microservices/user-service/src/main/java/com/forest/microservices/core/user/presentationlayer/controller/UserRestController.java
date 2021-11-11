package com.forest.microservices.core.user.presentationlayer.controller;

import com.forest.api.core.Movie.Movie;
import com.forest.api.core.User.User;
import com.forest.api.core.User.UserServiceAPI;
import com.forest.microservices.core.user.businesslayer.UserService;
import com.forest.microservices.core.user.datalayer.UserEntity;
import com.forest.utils.exceptions.InvalidInputException;
import com.forest.utils.exceptions.NotFoundException;
import com.forest.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController implements UserServiceAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getUser(int userId) {
        LOGGER.debug("/movie MS return the found user for userId: " + userId);

        if (userId < 1) throw new InvalidInputException("Invalid userId: " + userId);

        //if (userId == 14) throw  new NotFoundException("No user found for userId:" + userId);

        return userService.getUserById(userId);
    }

    @Override
    public User createUser(User model) {
        User user = userService.createUser(model);
        LOGGER.debug("REST createUser: request sent to user service for userId: {} ", user.getUserId());
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        LOGGER.debug("REST deleteUser: tried to delete userId: {} ", userId);
        userService.deleteUser(userId);
    }
}
