package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAstrologerResponseDto {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private Long mobile;
}
