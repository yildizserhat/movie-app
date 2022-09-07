package com.yildiz.serhat.movieservice.repository;

import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademyAwardRepository extends JpaRepository<AcademyAward, Long> {
    Optional<AcademyAward> findAcademyAwardByNomineeAndCategory(String nominee, String category);
}
