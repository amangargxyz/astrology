package com.project.astro.astrology.repository;

import com.project.astro.astrology.model.VoiceCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceCallRepository extends JpaRepository<VoiceCall, Long> {
    VoiceCall findByCallId(String callId);
}
