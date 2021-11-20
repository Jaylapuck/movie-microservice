package com.forest.api.core.User;

import com.forest.api.core.Movie.Movie;
import org.springframework.web.bind.annotation.*;

public interface UserServiceAPI {

    @GetMapping(
            value = "/user/{userId}",
            produces = "application/json"
    )
    User getUser(@PathVariable int userId);

    @PostMapping(
            value = "/user",
            consumes = "application/json",
            produces = "application/json")
    User createUser(@RequestBody User model);

    @DeleteMapping(
            value = "/user/{userId}"
    )
    void deleteUser(@PathVariable int userId);

    @PutMapping(
            value = "/user/{userId}",
            consumes = "application/json",
            produces = "application/json"
    )
    User updateUser(@PathVariable int userId, @RequestBody User model);
}
