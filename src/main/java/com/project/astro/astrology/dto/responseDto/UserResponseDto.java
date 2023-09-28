package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Long mobile;
    private String dob;
    private String birthTime;
    private String birthPlace;
    private Boolean isAstrologer;
    private Boolean isAdmin;
    private Boolean isApproved;
    private String role;
}
