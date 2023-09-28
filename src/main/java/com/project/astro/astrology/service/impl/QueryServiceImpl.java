package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.dto.requestDto.QueryRequestDto;
import com.project.astro.astrology.dto.responseDto.QueryResponseDto;
import com.project.astro.astrology.mapper.QueryMapper;
import com.project.astro.astrology.model.Astrologer;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.repository.AstrologerRepository;
import com.project.astro.astrology.repository.QueryRepository;
import com.project.astro.astrology.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private AstrologerRepository astrologerRepository;

    @Autowired
    private QueryMapper queryMapper;

    @Override
    public List<QueryResponseDto> getAllQueries() {
        List<Query> queries = queryRepository.findAll();
        queries.sort(Comparator.comparing(Query::getDate).reversed());
        return queries.stream().map(queryMapper::entityToResponseDto).toList();
    }

    @Override
    public Query getQueryById(Long id) {
        Optional<Query> opt = queryRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public List<QueryResponseDto> getQueriesByUserId(Long userId) {
        List<Query> queries = queryRepository.findByUserId(userId);
        queries.sort(Comparator.comparing(Query::getDate).reversed());
        return queries.stream().map(queryMapper::entityToResponseDto).toList();
    }

    @Override
    public List<QueryResponseDto> getAstrologerQueries(Long astrologerId) {
        Optional<Astrologer> astrologerOpt = astrologerRepository.findById(astrologerId);
        List<QueryResponseDto> queryResponseDtoList = new ArrayList<>();
        if(astrologerOpt.isPresent()) {
            Astrologer astrologer = astrologerOpt.get();
            List<Query> queries = astrologer.getQueries();
            queries.sort(Comparator.comparing(Query::getDate).reversed());
            queryResponseDtoList = queries.stream().map(queryMapper::entityToResponseDto).toList();
        }
        return queryResponseDtoList;
    }

    @Override
    public Query addQuery(QueryRequestDto queryDto) {
        Query query = queryMapper.requestDtoToEntity(queryDto);
        query.setAstrologers(new ArrayList<>());
        List<Astrologer> astrologerList = astrologerRepository.findAllById(queryDto.getAstrologerIds());
        for(Astrologer astrologer : astrologerList) {
            astrologer.addQuery(query);
        }
        query.setAstrologers(astrologerList);
        return queryRepository.save(query);
    }

    @Override
    public Boolean updateQuery(QueryRequestDto queryDto) {
        Optional<Query> opt = queryRepository.findById(queryDto.getId());
        if(opt.isPresent()) {
            Query oldQuery = opt.get();
            oldQuery.setQuery(queryDto.getQuery());
            oldQuery.setDate(queryDto.getDate());

            queryRepository.save(oldQuery);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteQuery(Long id) {
        Optional<Query> queryOtp = queryRepository.findById(id);
        List<Astrologer> astrologers = new ArrayList<>();
        if(queryOtp.isPresent()) {
            Query query = queryOtp.get();
            astrologers = query.getAstrologers();
        }
        while(astrologers.size() > 0) {
            Astrologer astrologer = astrologers.get(0);
            astrologer.removeQuery(id);
            astrologerRepository.save(astrologer);
        }

        queryRepository.deleteById(id);
    }
}
