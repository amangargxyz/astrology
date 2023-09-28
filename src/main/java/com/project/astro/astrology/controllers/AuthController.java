package com.project.astro.astrology.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.project.astro.astrology.model.*;
import com.project.astro.astrology.payload.request.LoginRequest;
import com.project.astro.astrology.payload.request.SignupRequest;
import com.project.astro.astrology.payload.response.JwtResponse;
import com.project.astro.astrology.payload.response.MessageResponse;
import com.project.astro.astrology.repository.AstrologerRepository;
import com.project.astro.astrology.repository.ClientRepository;
import com.project.astro.astrology.repository.RoleRepository;
import com.project.astro.astrology.repository.UserRepository;
import com.project.astro.astrology.security.jwt.JwtUtils;
import com.project.astro.astrology.security.services.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  AstrologerRepository astrologerRepository;

  @Autowired
  ClientRepository clientRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
    Map<String, String> map = new HashMap<>();
    if(userOpt.isPresent()) {
      User user = userOpt.get();
      if(!user.getIsApproved()) {
        map.put("message", "User not verified");
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
      }
    } else {
      map.put("message", "User not found");
      return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(
            signUpRequest.getFirstName(),
            signUpRequest.getLastName(),
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()),
            signUpRequest.getMobile(),
            signUpRequest.getDob(),
            signUpRequest.getBirthTime(),
            signUpRequest.getBirthPlace(),
            signUpRequest.getAstrologer(),
            signUpRequest.getAdmin(),
            signUpRequest.getApproved()
            );

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "astrologer" -> {
            Role astrologerRole = roleRepository.findByName(ERole.ROLE_ASTROLOGER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(astrologerRole);
          }
          default -> {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          }
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    if(!signUpRequest.getAdmin() && signUpRequest.getApproved()) {
      if(signUpRequest.getAstrologer()) {
        Astrologer astrologer = new Astrologer();
        astrologer.setUser(user);
        astrologerRepository.save(astrologer);
      } else {
        Client client = new Client();
        client.setUser(user);
        clientRepository.save(client);
      }
    }

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
