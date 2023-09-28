package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyResponseDto {
    private Long id;
    private String reply;
    private Timestamp date;
    private UserQueryResponseDto userQueryResponseDto;
}
