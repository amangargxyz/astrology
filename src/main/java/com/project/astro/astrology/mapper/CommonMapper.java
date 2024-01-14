package com.project.astro.astrology.mapper;

import com.project.astro.astrology.dto.responseDto.ReplyResponseDto;
import com.project.astro.astrology.dto.responseDto.UserAstrologerResponseDto;
import com.project.astro.astrology.dto.responseDto.UserClientResponseDto;
import com.project.astro.astrology.dto.responseDto.UserQueryResponseDto;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.model.Reply;
import com.project.astro.astrology.model.Role;
import com.project.astro.astrology.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface CommonMapper<S, D> {

    default String getRole(User user) {
        List<Role> rolesList = new ArrayList<>(user.getRoles());
        String role = String.valueOf(rolesList.get(0).getName());
        if(role.equals("ROLE_ADMIN")) {
            return "Admin";
        } else if(role.equals("ROLE_ASTROLOGER")) {
            return "Astrologer";
        } else {
            return "Client";
        }
    }

    default UserQueryResponseDto getUserQueryDto(User user) {
        UserQueryResponseDto userQueryResponseDto = new UserQueryResponseDto();
        userQueryResponseDto.setId(user.getId());
        userQueryResponseDto.setFullName(user.getFirstName() + " " + user.getLastName());
        userQueryResponseDto.setRole(getRole(user));

        return userQueryResponseDto;
    }

    default UserAstrologerResponseDto getUserAstrologerDto(User user) {
        UserAstrologerResponseDto userAstrologerResponseDto = new UserAstrologerResponseDto();
        userAstrologerResponseDto.setId(user.getId());
        userAstrologerResponseDto.setFullName(user.getFirstName() + " " + user.getLastName());
        userAstrologerResponseDto.setUsername(user.getUsername());
        userAstrologerResponseDto.setEmail(user.getEmail());
        userAstrologerResponseDto.setMobile(user.getMobile());
        userAstrologerResponseDto.setIsApproved(user.getIsApproved());

        return userAstrologerResponseDto;
    }

    default UserClientResponseDto getUserClientDto(User user) {
        UserClientResponseDto userClientResponseDto = new UserClientResponseDto();
        userClientResponseDto.setId(user.getId());
        userClientResponseDto.setFullName(user.getFirstName() + " " + user.getLastName());
        userClientResponseDto.setUsername(user.getUsername());
        userClientResponseDto.setEmail(user.getEmail());
        userClientResponseDto.setMobile(user.getMobile());

        return userClientResponseDto;
    }

    default List<ReplyResponseDto> getRepliesDtoList(Query query) {
        List<Reply> replies = query.getReplies();
        List<ReplyResponseDto> repliesDtoList = new ArrayList<>();

        for(Reply reply : replies) {
            ReplyResponseDto responseDto = new ReplyResponseDto();
            responseDto.setId(reply.getId());
            responseDto.setReply(reply.getReply());
            Date d = new Date(reply.getDate().getTime());
            responseDto.setDate(d.toString());
            responseDto.setUserQueryResponseDto(getUserQueryDto(reply.getUser()));
            responseDto.setAstrologerSeen(reply.getAstrologerSeen());
            responseDto.setClientSeen(reply.getClientSeen());

            repliesDtoList.add(responseDto);
        }
        return repliesDtoList;
    }

    default User setUser(Long userId) {
        User user = new User();
        user.setId(userId);

        return user;
    }

    default Query setQuery(Long queryId) {
        Query query = new Query();
        query.setId(queryId);

        return query;
    }
}
