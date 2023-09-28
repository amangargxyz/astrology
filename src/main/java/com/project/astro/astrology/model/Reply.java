package com.project.astro.astrology.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="query_id")
    private Query query;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private String reply;
    private Timestamp date;
}
