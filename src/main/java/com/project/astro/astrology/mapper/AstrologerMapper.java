package com.project.astro.astrology.mapper;

import com.project.astro.astrology.dto.responseDto.*;
import com.project.astro.astrology.model.Astrologer;
import com.project.astro.astrology.model.Query;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AstrologerMapper extends CommonMapper<Astrologer, AstrologerResponseDto> {

    @Mapping(target = "userAstrologerResponseDto", expression = "java(getUserAstrologerDto(astrologer))")
    @Mapping(target = "queries", expression = "java(getQueriesDtoList(astrologer))")
    AstrologerResponseDto entityToResponseDto(Astrologer astrologer);
//    Astrologer requestDtoToEntity(AstrologerRequestDto astrologerRequestDto);

    default UserAstrologerResponseDto getUserAstrologerDto(Astrologer astrologer) {
        return getUserAstrologerDto(astrologer.getUser());
    }

    default List<QueryResponseDto> getQueriesDtoList(Astrologer astrologer) {
        List<Query> queries = astrologer.getQueries();
        List<QueryResponseDto> queriesDtoList = new ArrayList<>();

        for(Query query : queries) {
            QueryResponseDto responseDto = new QueryResponseDto();
            responseDto.setId(query.getId());
            responseDto.setQuery(query.getQuery());
            responseDto.setDate(query.getDate());
            responseDto.setUserQueryResponseDto(getUserQueryDto(query.getUser()));
            responseDto.setReplies(getRepliesDtoList(query));

            queriesDtoList.add(responseDto);
        }
        return queriesDtoList;
    }
}
