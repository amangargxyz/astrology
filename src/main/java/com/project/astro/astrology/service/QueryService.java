package com.project.astro.astrology.service;

import com.project.astro.astrology.dto.requestDto.QueryRequestDto;
import com.project.astro.astrology.dto.responseDto.QueryResponseDto;
import com.project.astro.astrology.model.Query;

import java.util.List;

public interface QueryService {
    List<QueryResponseDto> getAllQueries();
    Query getQueryById(Long id);
    List<QueryResponseDto> getQueriesByUserId(Long userId);
    List<QueryResponseDto> getAstrologerQueries(Long astrologerId);
    Query addQuery(QueryRequestDto queryDto);
    Boolean updateQuery(QueryRequestDto queryDto);
    void deleteQuery(Long id);
}
