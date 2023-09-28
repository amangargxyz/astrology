package com.project.astro.astrology.repository;

import com.project.astro.astrology.model.Astrologer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AstrologerRepository extends JpaRepository<Astrologer, Long> {
}
