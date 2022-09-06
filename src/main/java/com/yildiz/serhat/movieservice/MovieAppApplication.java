package com.yildiz.serhat.movieservice;

import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import com.yildiz.serhat.movieservice.domain.model.CsvAcademyAwardModel;
import com.yildiz.serhat.movieservice.helper.CsvHelper;
import com.yildiz.serhat.movieservice.repository.AcademyAwardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class MovieAppApplication {

    private final AcademyAwardRepository academyAwardRepository;

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }

    @PostConstruct
    public void setup() {
        File csvFile = new File("src/main/resources/academy_awards.csv");
        List<CsvAcademyAwardModel> academyAwardModels = CsvHelper.convertFromFileToMovieAward(csvFile);
        List<AcademyAward> academyAwards = new ArrayList<>();
        academyAwardModels.forEach(academyAwardModel -> {
            AcademyAward academyAward = AcademyAward.buildAcademyAward(academyAwardModel);
            academyAwards.add(academyAward);
        });
        academyAwardRepository.saveAll(academyAwards);
    }
}
