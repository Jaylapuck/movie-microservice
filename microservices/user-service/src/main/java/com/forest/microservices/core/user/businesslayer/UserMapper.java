package com.forest.microservices.core.user.businesslayer;

import com.forest.api.core.User.User;
import com.forest.microservices.core.user.datalayer.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "serviceAddress", ignore=true)
    User entityToModel(UserEntity entity);

    @Mappings({
            @Mapping(target= "id", ignore = true),
            @Mapping(target="version", ignore = true)
    })
    UserEntity modelToEntity(User model);
}
