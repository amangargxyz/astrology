package com.project.astro.astrology.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.astro.astrology.model.ERole;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyRequestDto {
    private Long id;
    private Long queryId;
    private Long userId;
    private String userRole;
    private String reply;
    private Timestamp date;
}
