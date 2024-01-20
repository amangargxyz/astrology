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
import com.project.astro.astrology.service.LoggedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.*;

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

  @Autowired
  ActiveUserStore activeUserStore;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

    Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
    Map<String, String> map = new HashMap<>();
    if(userOpt.isEmpty()) {
      map.put("message", "User not found");
      return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//      if(!user.getIsApproved()) {
//        map.put("message", "User not verified");
//        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
//      }
    }
//    else {
//      map.put("message", "User not found");
//      return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//    }

    User currentUser = userOpt.get();
    ERole currentUserRole = currentUser.getRoles().stream().findFirst().orElse(null).getName();

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    HttpSession session = request.getSession(true);
    if (session != null) {
      LoggedUser user = new LoggedUser(authentication.getName(), currentUser.getId(), currentUserRole, activeUserStore);
      session.setAttribute("user", user);
    }

    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("/logout/{userId}")
  public ResponseEntity<?> logout(@PathVariable("userId") Long userId, HttpServletRequest request) {
//    try {
//      // Invalidate the session
//      request.getSession().invalidate();
//
//      // Clear authentication
//      SecurityContextHolder.clearContext();
//
//      Map<String, String> map = new HashMap<>();
//      map.put("message", "Logout successful");
//      return ResponseEntity.ok(map);
//    } catch (Exception e) {
//      Map<String, String> map = new HashMap<>();
//      map.put("message", "An error occurred during logout");
//      return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    HttpSession session = request.getSession();
    if (session != null){
      Map<Long, ERole> users = activeUserStore.getUsers();
      users.remove(userId);
      activeUserStore.setUsers(users);
//      session.removeAttribute("user");
    }

    return ResponseEntity.ok("User logout successfully");
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

//    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//      return ResponseEntity
//          .badRequest()
//          .body(new MessageResponse("Error: Email is already in use!"));
//    }

//    if (!signUpRequest.getMobileVerified()) {
//      return ResponseEntity
//              .badRequest()
//              .body(new MessageResponse("Error: Mobile is not verified!"));
//    }

    // Create new user's account
    User user = new User(
            signUpRequest.getFirstName(),
            signUpRequest.getLastName(),
            signUpRequest.getGender(),
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()),
            signUpRequest.getMobile(),
            signUpRequest.getDob(),
            signUpRequest.getBirthTime(),
            signUpRequest.getBirthPlace(),
            signUpRequest.getState(),
            signUpRequest.getCity(),
            signUpRequest.getAstrologer(),
            signUpRequest.getAdmin(),
            signUpRequest.getApproved()
    );
    if(signUpRequest.getAstrologer()) {
      Astrologer astrologer = new Astrologer();
      astrologer.setQualification(signUpRequest.getQualification());
      astrologer.setExperience(signUpRequest.getExperience());
      astrologer.setAstrologerService(signUpRequest.getAstrologerService().equals("PAID") ? EAstrologerService.PAID : EAstrologerService.FREE);
      astrologer.setFee(signUpRequest.getFee());
      user.setAstrologer(astrologer);
      astrologer.setUser(user);
    }

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

//    if(!signUpRequest.getAdmin() && signUpRequest.getApproved()) {
//      if(signUpRequest.getAstrologer()) {
//        Astrologer astrologer1 = new Astrologer();
//        astrologer1.setUser(user);
//        astrologerRepository.save(astrologer1);
//      } else {
//        Client client = new Client();
//        client.setUser(user);
//        clientRepository.save(client);
//      }
//    }
//    if(signUpRequest.getAstrologer()) {
//      Astrologer astrologer1 = new Astrologer();
//      astrologer1.setUser(user);
//      astrologerRepository.save(astrologer1);
//    } else {
//      Client client = new Client();
//      client.setUser(user);
//      clientRepository.save(client);
//    }
    if(!signUpRequest.getAstrologer()) {
      Client client = new Client();
      client.setUser(user);
      clientRepository.save(client);
    }

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

//  52. insert into roles table
//  INSERT INTO astrology_test_db.`role` (id, name) VALUES (1, 'ROLE_ADMIN');
//  INSERT INTO astrology_test_db.`role` (id, name) VALUES (2, 'ROLE_ASTROLOGER');
//  INSERT INTO astrology_test_db.`role` (id, name) VALUES (3, 'ROLE_USER');
//53. insert into users table, password - Admin!51
//  INSERT INTO astrology_test_db.users (id, birth_place, birth_time, city, dob, email, first_name, is_admin, is_approved, is_astrologer, last_name, mobile, password, state, username, gender) VALUES (1, 'Gwalior', '12:00', 'Gwalior', '1980-01-01T18:30:00.000Z', 'amangargxyz@gmail.com', 'admin', 1, 1, 0, NULL, 9340121421, '$2a$10$HYenb2iDX2WyDIeb.HOyvuUngAylwyTOam/2woeMSV1XA8QaDQMMy', 'Madhya Pradesh', 'admin', 'male');
//54. insert into user_roles table
//  INSERT INTO astrology_test_db.user_roles (user_id, role_id) VALUES (1, 1);
}
