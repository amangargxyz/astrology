package com.project.astro.astrology.mapper;

import com.project.astro.astrology.dto.responseDto.UserResponseDto;
import com.project.astro.astrology.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserMapper extends CommonMapper<User, UserResponseDto> {

    @Mapping(target = "role", expression = "java(getUserRole(user))")
    @Mapping(target = "dob", expression = "java(getDob(user))")
    UserResponseDto entityToResponseDto(User user);
    //User responseDtoToEntity(UserResponseDto responseDto);

    default String getUserRole(User user) {
        return getRole(user);
    }

    default String getDob(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        LocalDateTime dateTime = LocalDateTime.parse(user.getDob(), formatter);
        LocalDate date = dateTime.toLocalDate();

        return date.toString();
    }
}
