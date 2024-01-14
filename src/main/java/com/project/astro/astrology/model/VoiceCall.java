package com.project.astro.astrology.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class VoiceCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "call_id")
    private String callId;
    private List<Long> participants;
    private VoiceCallStatus status;
}
