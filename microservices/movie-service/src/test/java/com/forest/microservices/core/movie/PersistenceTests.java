package com.forest.microservices.core.movie;

import com.forest.microservices.core.movie.datalayer.MovieEntity;
import com.forest.microservices.core.movie.datalayer.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PersistenceTests {

    @Autowired
    private MovieRepository repository;

    private MovieEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        MovieEntity entity = new MovieEntity(1, "n", "d","08-23-2001");
        savedEntity = repository.save(entity);

        assertThat(savedEntity, samePropertyValuesAs(entity));
    }

    @Test
    public void createMovieEntity() {

        MovieEntity newEntity = new MovieEntity(2, "n", "d","04-23-2004");
        repository.save(newEntity);

        MovieEntity foundEntity = repository.findById(newEntity.getId()).get();

        assertThat(foundEntity, samePropertyValuesAs(newEntity));

        assertEquals(2, repository.count());
    }

    @Test
    public void updateMovieEntity() {
        savedEntity.setName("n2");
        repository.save(savedEntity);

        MovieEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("n2", foundEntity.getName());
    }

    @Test
    public void deleteMovieEntity() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }


    @Test
    public void getMovieEntity() {
        MovieEntity entity = repository.findByMovieId(savedEntity.getMovieId());

        assertThat(entity, samePropertyValuesAs(savedEntity));

    }
}
