package com.project.astro.astrology.service;

import com.project.astro.astrology.dto.responseDto.AstrologerResponseDto;
import com.project.astro.astrology.model.Astrologer;

import java.util.List;

public interface AstrologerService {
    List<AstrologerResponseDto> getAllAstrologers();
    Astrologer getAstrologerById(Long id);
    Astrologer addAstrologer(Astrologer astrologer);
    Astrologer updateAstrologer(Long id, Astrologer astrologer);
    void deleteAstrologer(Long id);
}
