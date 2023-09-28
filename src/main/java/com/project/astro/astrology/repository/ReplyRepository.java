package com.project.astro.astrology.repository;

import com.project.astro.astrology.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE r.user.id = :userId")
    List<Reply> findByUserId(@Param("userId") Long userId);

    void deleteByUserId(Long userId);
}
