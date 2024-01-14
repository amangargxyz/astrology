package com.project.astro.astrology.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Long mobile;
    private String dob;
    private String birthTime;
    private String birthPlace;
    private String state;
    private String city;
    private Boolean isAstrologer;
    private Boolean isAdmin;
    private Boolean isApproved;
}
