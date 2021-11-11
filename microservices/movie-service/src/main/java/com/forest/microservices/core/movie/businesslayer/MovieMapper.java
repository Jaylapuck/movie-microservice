package com.forest.microservices.core.movie.businesslayer;

import com.forest.api.core.Movie.Movie;
import com.forest.microservices.core.movie.datalayer.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring")
public interface MovieMapper {
        @Mapping(target = "serviceAddress", ignore = true)
        Movie entitytoModel(MovieEntity entity);

        @Mappings({
                @Mapping(target = "id", ignore = true),
                @Mapping(target = "version", ignore = true)
        })
        MovieEntity modelToEntity(Movie model);
}
