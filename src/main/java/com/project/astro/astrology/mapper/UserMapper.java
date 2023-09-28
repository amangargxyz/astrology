package com.project.astro.astrology.mapper;

import com.project.astro.astrology.dto.responseDto.UserResponseDto;
import com.project.astro.astrology.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends CommonMapper<User, UserResponseDto> {

    @Mapping(target = "role", expression = "java(getUserRole(user))")
    UserResponseDto entityToResponseDto(User user);
    //User responseDtoToEntity(UserResponseDto responseDto);

    default String getUserRole(User user) {
        return getRole(user);
    }
}
