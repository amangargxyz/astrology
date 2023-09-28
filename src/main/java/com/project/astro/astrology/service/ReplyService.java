package com.project.astro.astrology.service;

import com.project.astro.astrology.dto.requestDto.ReplyRequestDto;
import com.project.astro.astrology.model.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> getAllReplies();
    Reply getReplyById(Long id);
    Reply addReply(ReplyRequestDto replyRequestDto);
    Boolean updateReply(ReplyRequestDto replyDto);
    void deleteReply(Long id);
}
