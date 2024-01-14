package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.service.OtpService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
    private static final int OTP_LENGTH = 6;
    private static final String OTP_CHARACTERS = "0123456789";

    @Override
    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(OTP_CHARACTERS.length());
            otp.append(OTP_CHARACTERS.charAt(index));
        }

        return otp.toString();
    }

    @Override
    public void sendOtpToMobile(String mobile, String otp) {

    }

    @Override
    public void sendOtpToEmail(String email, String otp) {

    }
}
