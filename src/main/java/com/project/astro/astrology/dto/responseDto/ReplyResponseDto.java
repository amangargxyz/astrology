package com.project.astro.astrology.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyResponseDto {
    private Long id;
    private String reply;
    private String date;
    private UserQueryResponseDto userQueryResponseDto;
    private Boolean astrologerSeen;
    private Boolean clientSeen;
}
