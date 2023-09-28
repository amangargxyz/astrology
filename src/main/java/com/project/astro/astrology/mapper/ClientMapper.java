package com.project.astro.astrology.mapper;

import com.project.astro.astrology.dto.responseDto.ClientResponseDto;
import com.project.astro.astrology.dto.responseDto.UserClientResponseDto;
import com.project.astro.astrology.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper extends CommonMapper<Client, ClientResponseDto>{

    @Mapping(target = "user", expression = "java(getUserClientDto(client))")
    ClientResponseDto entityToResponseDto(Client client);

    default UserClientResponseDto getUserClientDto(Client client) {
        return getUserClientDto(client.getUser());
    }
}
