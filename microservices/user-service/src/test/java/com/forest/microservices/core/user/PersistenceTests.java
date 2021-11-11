package com.forest.microservices.core.user;

import com.forest.microservices.core.user.datalayer.UserEntity;
import com.forest.microservices.core.user.datalayer.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class PersistenceTests {

    @Autowired
    private UserRepository repository;
    private UserEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        UserEntity entity = new UserEntity(1,"name1","familyName1","Male","Address 1","Phone Number 1");
        savedEntity = repository.save(entity);

        assertThat(savedEntity, samePropertyValuesAs(entity));
    }

    @Test
    public void createUserEntity() {

        UserEntity newEntity = new UserEntity(2,"name2","familyName2","Female","Address 2","Phone Number 2");
        repository.save(newEntity);

        UserEntity foundEntity = repository.findById(newEntity.getId()).get();

        assertThat(foundEntity, samePropertyValuesAs(newEntity));

        assertEquals(2, repository.count());
    }

    @Test
    public void updateUserEntity() {
        savedEntity.setName("n2");
        repository.save(savedEntity);

        UserEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("n2", foundEntity.getName());
    }

    @Test
    public void deleteUserEntity() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }


    @Test
    public void getUserEntity() {
        Optional<UserEntity> entity = repository.findByUserId(savedEntity.getUserId());

        assertTrue(entity.isPresent());
        //assertEqualsProduct(savedEntity, entity.get());

        assertThat(entity.get(), samePropertyValuesAs(savedEntity));

    }
}
