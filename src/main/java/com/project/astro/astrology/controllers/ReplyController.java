package com.project.astro.astrology.controllers;

import com.project.astro.astrology.dto.requestDto.ReplyRequestDto;
import com.project.astro.astrology.model.Reply;
import com.project.astro.astrology.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/astrology/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllReplies() {
        return new ResponseEntity<>(replyService.getAllReplies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReplyById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(replyService.getReplyById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReply(@RequestBody ReplyRequestDto replyRequestDto) {
        if(replyRequestDto.getReply() != null && !replyRequestDto.getReply().isEmpty()) {
            return new ResponseEntity<>(replyService.addReply(replyRequestDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateReply(@RequestBody ReplyRequestDto replyDto) {
        Boolean updateSuccess;
        if(replyDto.getReply() != null && !replyDto.getReply().isEmpty()) {
            updateSuccess = replyService.updateReply(replyDto);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        if(updateSuccess) {
            return new ResponseEntity<>("Reply Updated Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable("id") Long id) {
        replyService.deleteReply(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
