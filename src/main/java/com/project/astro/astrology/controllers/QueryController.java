package com.project.astro.astrology.controllers;

import com.project.astro.astrology.dto.requestDto.QueryRequestDto;
import com.project.astro.astrology.dto.responseDto.QueryResponseDto;
import com.project.astro.astrology.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/astrology/query")
public class QueryController {
    @Autowired
    private QueryService queryService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllQueries() {
        List<QueryResponseDto> list = queryService.getAllQueries();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getQueryById(@PathVariable("id") Long id) {
//        return new ResponseEntity<>(queryService.getQueryById(id), HttpStatus.OK);
//    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getQueryByUserId(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(queryService.getQueriesByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/astrologer/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> getAstrologerQueries(@PathVariable("id") Long id) {
        return new ResponseEntity<>(queryService.getAstrologerQueries(id), HttpStatus.OK);
    }

    @GetMapping("/astrologer/chat/{userId}/{astrologerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> getAstrologerChat(@PathVariable("userId") Long userId,
                                               @PathVariable("astrologerId") Long astrologerId) {
        return new ResponseEntity<>(queryService.getAstrologerChat(userId, astrologerId), HttpStatus.OK);
    }

    @GetMapping("/new/message/{userId}/{userRole}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> getNewMessage(@PathVariable("userId") Long userId,
                                           @PathVariable("userRole") String userRole) {
        return new ResponseEntity<>(queryService.getNewMessage(userId, userRole), HttpStatus.OK);
    }

    @GetMapping("/astrologer/users/{astrologerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> getUsersForAstrologer(@PathVariable("astrologerId") Long astrologerId) {
        return new ResponseEntity<>(queryService.getUsersForAstrologer(astrologerId), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addQuery(@RequestBody QueryRequestDto queryDto) {
        if(queryDto.getQuery() != null && !queryDto.getQuery().isEmpty()) {
            return new ResponseEntity<>(queryService.addQuery(queryDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> updateQuery(@RequestBody QueryRequestDto queryDto) {
        Boolean updateSuccess = queryService.updateQuery(queryDto);
//        if(queryDto.getQuery() != null && !queryDto.getQuery().isEmpty()) {
//            updateSuccess = queryService.updateQuery(queryDto);
//        } else {
//            return new ResponseEntity<>(HttpStatus.OK);
//        }

        if(updateSuccess) {
            return new ResponseEntity<>("Query Updated Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuery(@PathVariable("id") Long id) {
        queryService.deleteQuery(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
