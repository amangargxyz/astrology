package com.project.astro.astrology.service;

import com.project.astro.astrology.dto.requestDto.QueryRequestDto;
import com.project.astro.astrology.dto.responseDto.QueryResponseDto;
import com.project.astro.astrology.dto.responseDto.UserClientResponseDto;
import com.project.astro.astrology.model.Query;

import java.util.List;
import java.util.Map;

public interface QueryService {
    List<QueryResponseDto> getAllQueries();
    Query getQueryById(Long id);
    List<QueryResponseDto> getQueriesByUserId(Long userId);
    List<QueryResponseDto> getAstrologerQueries(Long astrologerId);
    List<QueryResponseDto> getAstrologerChat(Long userId, Long astrologerId);
    Map<Long, Boolean> getNewMessage(Long userId, String userRole);
    List<UserClientResponseDto> getUsersForAstrologer(Long astrologerId);
    Query addQuery(QueryRequestDto queryDto);
    Boolean updateQuery(QueryRequestDto queryDto);
    void deleteQuery(Long id);
}
