package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResponseDto {
    private Long id;
    private String query;
    private String datetime;
    private UserQueryResponseDto userQueryResponseDto;
    private List<ReplyResponseDto> replies;
    private Boolean astrologerSeen;
    private Boolean clientSeen;
}
