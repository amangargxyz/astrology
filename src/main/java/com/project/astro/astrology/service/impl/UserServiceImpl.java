package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.dto.responseDto.UserResponseDto;
import com.project.astro.astrology.mapper.UserMapper;
import com.project.astro.astrology.model.Astrologer;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.model.User;
import com.project.astro.astrology.repository.AstrologerRepository;
import com.project.astro.astrology.repository.QueryRepository;
import com.project.astro.astrology.repository.ReplyRepository;
import com.project.astro.astrology.repository.UserRepository;
import com.project.astro.astrology.service.QueryService;
import com.project.astro.astrology.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AstrologerRepository astrologerRepository;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private QueryService queryService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            return userMapper.entityToResponseDto(opt.get());
        }
        return null;
    }

    @Override
    public List<UserResponseDto> getUnapprovedAstrologers() {
        List<User> users = userRepository.findAllUnapprovedAstrologers();
        return users.stream().map(userMapper::entityToResponseDto).toList();
    }

    @Override
    public Boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            User oldUser = opt.get();
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            oldUser.setMobile(user.getMobile());
            oldUser.setDob(user.getDob());
            oldUser.setBirthTime(user.getBirthTime());
            oldUser.setBirthPlace(user.getBirthPlace());

            return userRepository.save(oldUser);
        }

        return null;
    }

    @Override
    public Boolean approveUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            user.setIsApproved(true);
            userRepository.save(user);

            Astrologer astrologer = new Astrologer();
            astrologer.setUser(user);
            astrologerRepository.save(astrologer);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void denyUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserResponseDto user = getUserById(id);

        if(user.getRole().equals("Client")) {
            List<Query> queries = queryRepository.findByUserId(id);
            List<Long> queryIds = queries.stream().map(Query::getId).toList();
            for(Long queryId : queryIds) {
                queryService.deleteQuery(queryId);
            }
        } else if(user.getRole().equals("Astrologer")) {
            replyRepository.deleteByUserId(id);
        }
        userRepository.deleteById(id);
    }

}
