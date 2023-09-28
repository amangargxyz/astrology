package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.dto.requestDto.ReplyRequestDto;
import com.project.astro.astrology.mapper.ReplyMapper;
import com.project.astro.astrology.model.Reply;
import com.project.astro.astrology.repository.ReplyRepository;
import com.project.astro.astrology.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return replyRepository.findAll();
    }

    @Override
    public Reply getReplyById(Long id) {
        Optional<Reply> opt = replyRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Reply addReply(ReplyRequestDto replyRequestDto) {
        Reply reply = replyMapper.requestDtoToEntity(replyRequestDto);
        return replyRepository.save(reply);
    }

    @Override
    public Boolean updateReply(ReplyRequestDto replyDto) {
        Optional<Reply> opt = replyRepository.findById(replyDto.getId());
        if(opt.isPresent()) {
            Reply oldReply = opt.get();
            oldReply.setReply(replyDto.getReply());
            oldReply.setDate(replyDto.getDate());
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
