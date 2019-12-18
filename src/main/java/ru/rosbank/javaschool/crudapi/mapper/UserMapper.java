package ru.rosbank.javaschool.crudapi.mapper;

import org.mapstruct.Mapper;
import ru.rosbank.javaschool.crudapi.dto.UserResponseDto;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto entityToUserResponseDto(UserEntity entity);
}
