package com.project.astro.astrology.service;

import com.project.astro.astrology.dto.responseDto.UserResponseDto;
import com.project.astro.astrology.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    UserResponseDto getUserById(Long id);
    List<UserResponseDto> getUnapprovedAstrologers();
    Boolean isUsernameExist(String username);
    User addUser(User user);
    User updateUser(Long id, User user);
    Boolean approveUser(Long id);
    void denyUser(Long id);
    void deleteUser(Long id);
}
