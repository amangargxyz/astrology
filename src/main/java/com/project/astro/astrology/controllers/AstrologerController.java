package com.project.astro.astrology.controllers;

import com.project.astro.astrology.service.AstrologerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/astrology/astrologer")
public class AstrologerController {
    @Autowired
    private AstrologerService astrologerService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> getAllAstrologers() {
        return new ResponseEntity<>(astrologerService.getAllAstrologers(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getAstrologerById(@PathVariable("id") Long id) {
//        return new ResponseEntity<>(astrologerService.getAstrologerById(id), HttpStatus.OK);
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addAstrologer(@RequestBody Astrologer astrologer) {
//        return new ResponseEntity<>(astrologerService.addAstrologer(astrologer), HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateAstrologer(@PathVariable("id") Long id, @RequestBody Astrologer astrologer) {
//        return new ResponseEntity<>(astrologerService.updateAstrologer(id, astrologer), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteAstrologer(@PathVariable("id") Long id) {
//        astrologerService.deleteAstrologer(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
