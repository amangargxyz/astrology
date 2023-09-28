package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.dto.responseDto.AstrologerResponseDto;
import com.project.astro.astrology.mapper.AstrologerMapper;
import com.project.astro.astrology.model.Astrologer;
import com.project.astro.astrology.repository.AstrologerRepository;
import com.project.astro.astrology.service.AstrologerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AstrologerServiceImpl implements AstrologerService {

    @Autowired
    private AstrologerRepository astrologerRepository;

    @Autowired
    private AstrologerMapper astrologerMapper;

    @Override
    public List<AstrologerResponseDto> getAllAstrologers() {
        List<Astrologer> astrologers = astrologerRepository.findAll();
        return astrologers.stream().map(astrologerMapper::entityToResponseDto).toList();
    }

    @Override
    public Astrologer getAstrologerById(Long id) {
        Optional<Astrologer> opt = astrologerRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Astrologer addAstrologer(Astrologer astrologer) {
        return astrologerRepository.save(astrologer);
    }

    @Override
    public Astrologer updateAstrologer(Long id, Astrologer astrologer) {
        Optional<Astrologer> opt = astrologerRepository.findById(id);
        if(opt.isPresent()) {
            Astrologer oldAstrologer = opt.get();
            oldAstrologer.setUser(astrologer.getUser());

            return astrologerRepository.save(oldAstrologer);
        }

        return null;
    }

    @Override
    public void deleteAstrologer(Long id) {
        astrologerRepository.deleteById(id);
    }
}
