package com.yildiz.serhat.movieservice.repository;

import com.yildiz.serhat.movieservice.TestInitializer;
import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import com.yildiz.serhat.movieservice.domain.enums.MovieAwardCategory;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = TestInitializer.class)
class AcademyAwardRepositoryTest {

    @Autowired
    private AcademyAwardRepository academyAwardRepository;

    @BeforeEach
    void setup() {
        academyAwardRepository.deleteAll();
    }

    @AfterEach
    void tearDown(){
        academyAwardRepository.deleteAll();
    }

    @Test
    void shouldFindAcademyAwardByNomineeAndCategory() {
        AcademyAward academyAward1 = AcademyAward.builder()
                .category(MovieAwardCategory.BEST_PICTURE.getValue())
                .won(false)
                .year("2011")
                .nominee("The Social Network")
                .build();
        AcademyAward academyAward2 = AcademyAward.builder()
                .category(MovieAwardCategory.BEST_PICTURE.getValue())
                .won(false)
                .year("2011")
                .nominee("Black Swan")
                .build();
        AcademyAward academyAward3 = AcademyAward.builder()
                .category(MovieAwardCategory.BEST_PICTURE.getValue())
                .won(true)
                .year("2011")
                .nominee("The Fighter")
                .build();

        academyAwardRepository.saveAll(Arrays.asList(academyAward1, academyAward2, academyAward3));

        Optional<AcademyAward> blackSwan = academyAwardRepository.findAcademyAwardByNomineeAndCategory("Black Swan", MovieAwardCategory.BEST_PICTURE.getValue());

        assertEquals(academyAward2.getNominee(), blackSwan.get().getNominee());
        assertEquals(academyAward2.getYear(), blackSwan.get().getYear());
        assertEquals(academyAward2.getCategory(), blackSwan.get().getCategory());
    }

}