package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.dto.requestDto.QueryRequestDto;
import com.project.astro.astrology.dto.responseDto.QueryResponseDto;
import com.project.astro.astrology.dto.responseDto.UserClientResponseDto;
import com.project.astro.astrology.mapper.QueryMapper;
import com.project.astro.astrology.model.Astrologer;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.model.Reply;
import com.project.astro.astrology.model.User;
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
        List<QueryResponseDto> list = queries.stream().map(queryMapper::entityToResponseDto).toList();
        return list;
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
    public List<QueryResponseDto> getAstrologerChat(Long userId, Long astrologerId) {
        Optional<Astrologer> astrologerOpt = astrologerRepository.findById(astrologerId);
        List<QueryResponseDto> queryResponseDtoList = new ArrayList<>();
        if(astrologerOpt.isPresent()) {
            Astrologer astrologer = astrologerOpt.get();
            List<Query> queries = astrologer.getQueries();
            List<Query> chat = new ArrayList<>();
            for(Query query : queries) {
                if(Objects.equals(query.getUser().getId(), userId)) {
                    chat.add(query);
                }
            }
            chat.sort(Comparator.comparing(Query::getDate).reversed());
            queryResponseDtoList = chat.stream().map(queryMapper::entityToResponseDto).toList();
        }
        return queryResponseDtoList;
    }

    @Override
    public Map<Long, Boolean> getNewMessage(Long userId, String userRole) {
        Map<Long, Boolean> map = new HashMap<>();
        if(userRole.equals("ROLE_ASTROLOGER")) {
            Optional<Astrologer> astrologerOpt = astrologerRepository.findById(userId);

            if(astrologerOpt.isPresent()) {
                Astrologer astrologer = astrologerOpt.get();
                List<Query> queries = astrologer.getQueries();
                Map<Long, List<Query>> chatMap = new HashMap<>();
                for(Query query : queries) {
                    User user = query.getUser();
                    if(!map.containsKey(user.getId())) {
                        map.put(user.getId(), false);
                    }
                    if(!chatMap.containsKey(user.getId())) {
                        List<Query> userQuery = new ArrayList<>();
                        userQuery.add(query);
                        chatMap.put(user.getId(), userQuery);
                    } else {
                        List<Query> userQuery = chatMap.get(user.getId());
                        userQuery.add(query);
                        chatMap.put(user.getId(), userQuery);
                    }
                }
                for(Long id : map.keySet()) {
                    List<Query> userChat = chatMap.get(id);
                    for(Query query : userChat) {
                        if(!query.getAstrologerSeen()) {
                            map.put(id, true);
                        }
                        for(Reply reply : query.getReplies()) {
                            if(!reply.getAstrologerSeen()) {
                                map.put(id, true);
                            }
                        }
                    }
                }
            }
        } else if(userRole.equals("ROLE_USER")) {
            List<Astrologer> astrologerList = astrologerRepository.findAll();
            Map<Long, List<Query>> chatMap = new HashMap<>();
            for(Astrologer astrologer : astrologerList) {
                List<Query> queries = astrologer.getQueries();
                List<Query> userChat = new ArrayList<>();
                for(Query query : queries) {
                    User queryUser = query.getUser();
                    if(Objects.equals(queryUser.getId(), userId)) {
                        userChat.add(query);
                    }
                }
                chatMap.put(astrologer.getId(), userChat);
                map.put(astrologer.getId(), false);
            }
            for(Long id : map.keySet()) {
                List<Query> userChat = chatMap.get(id);
                for(Query query : userChat) {
                    if(!query.getClientSeen()) {
                        map.put(id, true);
                    }
                    for(Reply reply : query.getReplies()) {
                        if(!reply.getClientSeen()) {
                            map.put(id, true);
                        }
                    }
                }
            }
        }

        return map;
    }

    @Override
    public List<UserClientResponseDto> getUsersForAstrologer(Long astrologerId) {
        Optional<Astrologer> astrologerOpt = astrologerRepository.findById(astrologerId);
        List<UserClientResponseDto> userClientResponseDtoList = new ArrayList<>();
        if(astrologerOpt.isPresent()) {
            Astrologer astrologer = astrologerOpt.get();
            List<Query> queries = astrologer.getQueries();
            for(Query query : queries) {
                User user = query.getUser();
                UserClientResponseDto dto = new UserClientResponseDto();
                dto.setId(user.getId());
                dto.setFullName(user.getFirstName() + " " + user.getLastName());
                dto.setUsername(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setMobile(user.getMobile());
                if(!userClientResponseDtoList.contains(dto)) {
                    userClientResponseDtoList.add(dto);
                }
            }
        }
        return userClientResponseDtoList;
    }

    @Override
    public Query addQuery(QueryRequestDto queryDto) {
        Query query = queryMapper.requestDtoToEntity(queryDto);
        query.setAstrologerSeen(false);
        query.setClientSeen(true);
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
            if(queryDto.getQuery() != null && !queryDto.getQuery().isEmpty()) {
                oldQuery.setQuery(queryDto.getQuery());
            }
            if(queryDto.getDate() != null) {
                oldQuery.setDate(queryDto.getDate());
            }
            if (oldQuery.getAstrologerSeen().equals(false)) {
                oldQuery.setAstrologerSeen(true);
            }

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
