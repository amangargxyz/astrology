package com.project.astro.astrology.service;

public interface OtpService {
    String generateOtp();

    void sendOtpToMobile(String mobile, String otp);

    void sendOtpToEmail(String email, String otp);
}
