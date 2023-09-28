package com.project.astro.astrology.mapper;

import com.project.astro.astrology.dto.requestDto.ReplyRequestDto;
import com.project.astro.astrology.dto.responseDto.ReplyResponseDto;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.model.Reply;
import com.project.astro.astrology.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplyMapper extends CommonMapper<Reply, ReplyResponseDto>{

    @Mapping(target = "query", expression = "java(setReplyQuery(replyRequestDto))")
    @Mapping(target = "user", expression = "java(setReplyUser(replyRequestDto))")
    Reply requestDtoToEntity(ReplyRequestDto replyRequestDto);

    default Query setReplyQuery(ReplyRequestDto replyRequestDto) {
        return setQuery(replyRequestDto.getQueryId());
    }
    default User setReplyUser(ReplyRequestDto replyRequestDto) {
        return setUser(replyRequestDto.getUserId());
    }
}
