package com.project.astro.astrology.controllers;

import com.project.astro.astrology.dto.requestDto.ReplyRequestDto;
import com.project.astro.astrology.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/astrology/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllReplies() {
//        return new ResponseEntity<>(replyService.getAllReplies(), HttpStatus.OK);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getReplyById(@PathVariable("id") Long id) {
//        return new ResponseEntity<>(replyService.getReplyById(id), HttpStatus.OK);
//    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> addReply(@RequestBody ReplyRequestDto replyRequestDto) {
        if(replyRequestDto.getReply() != null && !replyRequestDto.getReply().isEmpty()) {
            return new ResponseEntity<>(replyService.addReply(replyRequestDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> updateReply(@RequestBody ReplyRequestDto replyDto) {
        Boolean updateSuccess = replyService.updateReply(replyDto);
//        if(replyDto.getReply() != null && !replyDto.getReply().isEmpty()) {
//            updateSuccess = replyService.updateReply(replyDto);
//        } else {
//            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
//        }

        if(updateSuccess) {
            return new ResponseEntity<>("Reply Updated Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteReply(@PathVariable("id") Long id) {
        replyService.deleteReply(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
