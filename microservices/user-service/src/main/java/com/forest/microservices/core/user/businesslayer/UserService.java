package com.forest.microservices.core.user.businesslayer;

import com.forest.api.core.User.User;

public interface UserService {

    User getUserById(int userId);

    User createUser(User model);

    void deleteUser(int userId);
}
