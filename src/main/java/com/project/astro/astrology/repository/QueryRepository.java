package com.project.astro.astrology.repository;

import com.project.astro.astrology.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {

    @org.springframework.data.jpa.repository.Query("SELECT q FROM Query q WHERE q.user.id = :userId")
    List<Query> findByUserId(@Param("userId") Long userId);
}
