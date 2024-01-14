package com.project.astro.astrology.controllers;

import com.project.astro.astrology.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private VerifyService verifyService;

    private Map<String, String> otpStorage = new ConcurrentHashMap<>();

    @GetMapping("/mobile/generate-otp/{mobile}")
    public ResponseEntity<String> generateMobileOtp(@PathVariable("mobile") String mobile) {
        verifyService.sendVerificationViaSms(mobile);
        return ResponseEntity.ok("OTP sent successfully to " + mobile);
    }

    @PostMapping("/email/generate-otp/{email}")
    public ResponseEntity<String> generateEmailOtp(@PathVariable("email") String email) {
//        verifyService.sendVerificationViaEmail(email);

        return ResponseEntity.ok("OTP sent successfully to " + email);
    }

    @GetMapping("/mobile/verify-otp/{mobile}/{otp}")
    public ResponseEntity<?> verifyMobileOtp(@PathVariable("mobile") String mobile,
                                                  @PathVariable("otp") String userOtp) {

        return new ResponseEntity<>(verifyService.verifyMobile(mobile, userOtp), HttpStatus.OK);
    }

    @PostMapping("/email/verify-otp/{email}/{otp}")
    public ResponseEntity<String> verifyEmailOtp(@PathVariable("email") String email,
                                                  @PathVariable("otp") String userOtp) {

        return new ResponseEntity<>("verifyService.verifyEmail(email, userOtp)", HttpStatus.OK);
    }
}
