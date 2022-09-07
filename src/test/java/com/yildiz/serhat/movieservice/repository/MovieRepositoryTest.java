package com.yildiz.serhat.movieservice.repository;

import com.yildiz.serhat.movieservice.TestInitializer;
import com.yildiz.serhat.movieservice.domain.entity.Movie;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ContextConfiguration(initializers = TestInitializer.class)
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setup() {
        movieRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
    }

    @Test
    void shouldFindMovieByTitle() {
        Movie movie1 = Movie.builder()
                .boxOffice(valueOf(123123))
                .title("Black Swan")
                .year(2011)
                .build();

        Movie movie2 = Movie.builder()
                .boxOffice(valueOf(456456))
                .title("The Social NetWork")
                .year(2011)
                .build();

        Movie movie3 = Movie.builder()
                .boxOffice(valueOf(3444))
                .title("The Fighter")
                .year(2011)
                .build();

        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3));

        Optional<Movie> blackSwan = movieRepository.findMovieByTitle("Black Swan");

        assertEquals(movie1.getTitle(), blackSwan.get().getTitle());
        assertEquals(movie1.getBoxOffice(), blackSwan.get().getBoxOffice().setScale(0));
        assertEquals(movie1.getYear(), blackSwan.get().getYear());
    }
}