package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AstrologerResponseDto {
    private Long id;
    private UserAstrologerResponseDto userAstrologerResponseDto;
    private List<QueryResponseDto> queries;
}
