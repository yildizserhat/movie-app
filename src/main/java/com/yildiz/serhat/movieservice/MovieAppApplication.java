package com.yildiz.serhat.movieservice;

import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import com.yildiz.serhat.movieservice.repository.AcademyAwardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.yildiz.serhat.movieservice.helper.CsvHelper.createAcademyAwards;

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
        if (academyAwardRepository.findAll().size() > 0) {
            return;
        }
        List<AcademyAward> academyAwards = createAcademyAwards();
        academyAwardRepository.saveAll(academyAwards);
    }
}
