package com.yildiz.serhat.movieservice.service;

import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;

import java.util.Optional;

public interface AcademyAwardService {

    Optional<AcademyAward> findAcademyAwardByNomineeAndCategory(String nominee, String category);
}
