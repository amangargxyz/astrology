package com.project.astro.astrology.mapper;

import com.project.astro.astrology.dto.requestDto.QueryRequestDto;
import com.project.astro.astrology.dto.responseDto.QueryResponseDto;
import com.project.astro.astrology.dto.responseDto.ReplyResponseDto;
import com.project.astro.astrology.dto.responseDto.UserQueryResponseDto;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.model.Reply;
import com.project.astro.astrology.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QueryMapper extends CommonMapper<Query, QueryResponseDto>{

    @Mapping(target = "userQueryResponseDto", expression = "java(getUserQueryDto(query))")
    @Mapping(target = "replies", expression = "java(getReplyDtoList(query))")
    QueryResponseDto entityToResponseDto(Query query);
    @Mapping(target = "user", expression = "java(setQueryUser(queryRequestDto))")
    Query requestDtoToEntity(QueryRequestDto queryRequestDto);

    default UserQueryResponseDto getUserQueryDto(Query query) {
        return getUserQueryDto(query.getUser());
    }

    default List<ReplyResponseDto> getReplyDtoList(Query query) {
        return getRepliesDtoList(query);
    }

    default User setQueryUser(QueryRequestDto queryRequestDto) {
        return setUser(queryRequestDto.getUserId());
    }
}
