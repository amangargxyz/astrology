package com.project.astro.astrology.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRequestDto {
    private Long id;
    private Long userId;
}
