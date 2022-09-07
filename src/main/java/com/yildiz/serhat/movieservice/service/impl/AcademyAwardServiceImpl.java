package com.yildiz.serhat.movieservice.service.impl;

import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import com.yildiz.serhat.movieservice.repository.AcademyAwardRepository;
import com.yildiz.serhat.movieservice.service.AcademyAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcademyAwardServiceImpl implements AcademyAwardService {

    private final AcademyAwardRepository academyAwardRepository;

    @Override
    public Optional<AcademyAward> findAcademyAwardByNomineeAndCategory(String nominee, String category) {
        return academyAwardRepository.findAcademyAwardByNomineeAndCategory(nominee, category);
    }
}