package com.project.astro.astrology.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryRequestDto {
    private Long id;
    private Long userId;
    private String query;
    private Timestamp date;
    private List<Long> astrologerIds;
}
