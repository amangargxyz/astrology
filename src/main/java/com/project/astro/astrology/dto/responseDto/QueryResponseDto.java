package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResponseDto {
    private Long id;
    private String query;
    private Timestamp date;
    private UserQueryResponseDto userQueryResponseDto;
    private List<ReplyResponseDto> replies;
}
