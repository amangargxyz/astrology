package com.project.astro.astrology.controllers;

import com.project.astro.astrology.model.ActiveUserStore;
import com.project.astro.astrology.model.User;
import com.project.astro.astrology.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/astrology/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    ActiveUserStore activeUserStore;

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllUsers() {
//        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.isUsernameExist(username), HttpStatus.OK);
    }

    @GetMapping("/astrologer/unapproved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUnapprovedAstrologers() {
        return new ResponseEntity<>(userService.getUnapprovedAstrologers(), HttpStatus.OK);
    }

    @GetMapping("/loggedUsers")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('ASTROLOGER')")
    public Object getLoggedUsers(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return model.getAttribute("users");
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
//        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
//        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
//    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.approveUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/deny/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> denyUser(@PathVariable("id") Long id) {
        userService.denyUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}