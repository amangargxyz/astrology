package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.astro.astrology.model.EAstrologerService;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AstrologerResponseDto {
    private Long id;
    private String qualification;
    private String experience = "0";
    private EAstrologerService astrologerService;
    private String fee;
    private UserAstrologerResponseDto userAstrologerResponseDto;
    private List<QueryResponseDto> queries;
}
