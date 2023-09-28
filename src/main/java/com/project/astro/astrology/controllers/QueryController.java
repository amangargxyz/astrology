package com.project.astro.astrology.controllers;

import com.project.astro.astrology.dto.requestDto.QueryRequestDto;
import com.project.astro.astrology.dto.responseDto.QueryResponseDto;
import com.project.astro.astrology.model.Query;
import com.project.astro.astrology.service.QueryService;
import org.apache.tomcat.util.http.parser.HttpParser;
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
    public ResponseEntity<?> getAllQueries() {
        List<QueryResponseDto> list = queryService.getAllQueries();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQueryById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(queryService.getQueryById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> getQueryByUserId(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(queryService.getQueriesByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/astrologer/{id}")
    public ResponseEntity<?> getAstrologerQueries(@PathVariable("id") Long id) {
        return new ResponseEntity<>(queryService.getAstrologerQueries(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addQuery(@RequestBody QueryRequestDto queryDto) {
        if(queryDto.getQuery() != null && !queryDto.getQuery().isEmpty()) {
            return new ResponseEntity<>(queryService.addQuery(queryDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuery(@RequestBody QueryRequestDto queryDto) {
        Boolean updateSuccess;
        if(queryDto.getQuery() != null && !queryDto.getQuery().isEmpty()) {
            updateSuccess = queryService.updateQuery(queryDto);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        if(updateSuccess) {
            return new ResponseEntity<>("Query Updated Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuery(@PathVariable("id") Long id) {
        queryService.deleteQuery(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
