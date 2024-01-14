package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.dto.requestDto.ReplyRequestDto;
import com.project.astro.astrology.mapper.ReplyMapper;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.model.Reply;
import com.project.astro.astrology.repository.ReplyRepository;
import com.project.astro.astrology.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> getAllReplies() {
        List<Reply> replies = replyRepository.findAll();
        replies.sort(Comparator.comparing(Reply::getDate).reversed());
        return replies;
    }

    @Override
    public Reply getReplyById(Long id) {
        Optional<Reply> opt = replyRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Reply addReply(ReplyRequestDto replyRequestDto) {
        Reply reply = replyMapper.requestDtoToEntity(replyRequestDto);
        if(replyRequestDto.getUserRole().equals("ROLE_ASTROLOGER")) {
            reply.setAstrologerSeen(true);
            reply.setClientSeen(false);
        } else {
            reply.setAstrologerSeen(false);
            reply.setClientSeen(true);
        }
        return replyRepository.save(reply);
    }

    @Override
    public Boolean updateReply(ReplyRequestDto replyDto) {
        Optional<Reply> opt = replyRepository.findById(replyDto.getId());
        if(opt.isPresent()) {
            Reply oldReply = opt.get();
            if(replyDto.getReply() != null && !replyDto.getReply().isEmpty()) {
                oldReply.setReply(replyDto.getReply());
            }
            if(replyDto.getDate() != null) {
                oldReply.setDate(replyDto.getDate());
            }
            if(replyDto.getUserRole() != null && replyDto.getUserRole().equals("ROLE_USER")) {
                oldReply.setClientSeen(true);
            }
            if(replyDto.getUserRole() != null && replyDto.getUserRole().equals("ROLE_ASTROLOGER")) {
                oldReply.setAstrologerSeen(true);
            }
            replyRepository.save(oldReply);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteReply(Long id) {
        replyRepository.deleteById(id);
    }
}
