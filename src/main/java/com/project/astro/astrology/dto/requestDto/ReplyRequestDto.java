package com.project.astro.astrology.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyRequestDto {
    private Long id;
    private Long queryId;
    private Long userId;
    private String reply;
    private Timestamp date;
}
