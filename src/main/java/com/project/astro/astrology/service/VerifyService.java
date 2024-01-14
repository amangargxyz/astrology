package com.project.astro.astrology.service;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

@org.springframework.stereotype.Service
public class VerifyService {
    public static final String ACCOUNT_SID = "AC276f2f6a0d101deb3f80aead55556998";
    public static final String AUTH_TOKEN = "c61c0f2086be8c7b3420b920576685db";

    public String createService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Service service = Service.creator("Otp Verify Service").create();
        return service.getSid();
    }

    public String getMobileServiceId() {
        return "VA3dc5644bd9342ac6cdb8f4c77a1bb0ac";
    }

    public void sendVerificationViaSms(String mobile) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String service_sid = getMobileServiceId();
        Verification verification = Verification.creator(
                        service_sid,
                        "+91" + mobile,
                        "sms")
                .create();
    }

    public String verifyMobile(String mobile, String userOtp) {
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        getMobileServiceId())
                .setTo("+91" + mobile)
                .setCode(userOtp)
                .create();
        if(verificationCheck.getStatus().equals("approved")) {
            return "Otp is verified";
        } else {
            return "Invalid Otp";
        }
    }
}
