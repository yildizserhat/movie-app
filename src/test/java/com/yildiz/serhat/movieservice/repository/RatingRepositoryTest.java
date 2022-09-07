package com.yildiz.serhat.movieservice.repository;

import com.yildiz.serhat.movieservice.TestInitializer;
import com.yildiz.serhat.movieservice.domain.entity.Movie;
import com.yildiz.serhat.movieservice.domain.entity.Rating;
import com.yildiz.serhat.movieservice.domain.entity.User;
import com.yildiz.serhat.movieservice.domain.model.TopMoviesResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = TestInitializer.class)
class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        ratingRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        ratingRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldGetTopMovies() {
        String email = "yildiz_serhat@hotmail.com";

        Movie movie = Movie.builder()
                .title("Black Swan")
                .year(2011)
                .boxOffice(valueOf(123123))
                .build();

        User user = User.builder()
                .email(email)
                .token("123451234aaa")
                .build();

        Rating rating = Rating.builder()
                .rating(5.5)
                .movie(movie)
                .user(user).build();

        userRepository.save(user);
        movieRepository.save(movie);
        ratingRepository.save(rating);

        movieRepository.findAll();
        Pageable pageable = PageRequest.of(0, 2);

        List<TopMoviesResponseModel> ratingByUserOrderByBoxOfficeValue = ratingRepository.getRatingByUserOrderByBoxOfficeValue(email, pageable);
        TopMoviesResponseModel topMoviesResponseModel = ratingByUserOrderByBoxOfficeValue.get(0);

        assertEquals(email, topMoviesResponseModel.getEmail());
        assertEquals(rating.getRating(), topMoviesResponseModel.getRating());
        assertEquals(movie.getBoxOffice(), topMoviesResponseModel.getBoxOffice().setScale(0));
        assertEquals(movie.getTitle(), topMoviesResponseModel.getTitle());
    }

    @Test
    void shouldGetTopMoviesWithLimit() {
        String email = "yildiz_serhat@hotmail.com";
        User user = User.builder()
                .email(email)
                .token("123451234aaa")
                .build();

        Movie movie1 = Movie.builder()
                .title("Black Swan")
                .year(2011)
                .boxOffice(valueOf(123123))
                .build();

        Movie movie2 = Movie.builder()
                .title("The Fighter")
                .year(2011)
                .boxOffice(valueOf(12344))
                .build();


        Rating rating1 = Rating.builder()
                .rating(5.5)
                .movie(movie1)
                .user(user).build();

        Rating rating2 = Rating.builder()
                .rating(4)
                .movie(movie2)
                .user(user).build();

        userRepository.save(user);
        movieRepository.saveAll(Arrays.asList(movie1, movie2));
        ratingRepository.saveAll(Arrays.asList(rating1, rating2));

        movieRepository.findAll();
        Pageable pageable = PageRequest.of(0, 1);

        List<TopMoviesResponseModel> ratingByUserOrderByBoxOfficeValue = ratingRepository.getRatingByUserOrderByBoxOfficeValue(email, pageable);

        assertEquals(1, ratingByUserOrderByBoxOfficeValue.size());
    }

}