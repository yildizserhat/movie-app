package com.yildiz.serhat.movieservice.service.impl;

import com.yildiz.serhat.movieservice.client.omdb.OmdbClient;
import com.yildiz.serhat.movieservice.domain.entity.Movie;
import com.yildiz.serhat.movieservice.domain.entity.User;
import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import com.yildiz.serhat.movieservice.exception.UserNotFoundException;
import com.yildiz.serhat.movieservice.repository.RatingRepository;
import com.yildiz.serhat.movieservice.service.MovieService;
import com.yildiz.serhat.movieservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private OmdbClient omdbClient;

    @Mock
    private MovieService movieService;

    @Mock
    private UserService userService;

    @Test
    void shouldRateMovie() {
        OmdbResponse omdbResponse = OmdbResponse.builder()
                .boxOffice("$623,357,910")
                .title("The Avengers")
                .year("2012")
                .build();

        Movie movie = Movie.builder()
                .boxOffice(BigDecimal.valueOf(623357910))
                .year(2012)
                .title("The Avengers")
                .build();

        User user = User.builder()
                .token("token")
                .email("yildiz_serhat@hotmail.comö")
                .build();

        when(omdbClient.findMovieByTitle("The Avengers")).thenReturn(omdbResponse);
        when(movieService.saveMovieIfNotExist(Movie.createMovie(omdbResponse))).thenReturn(movie);
        when(userService.findUserByToken("token")).thenReturn(Optional.of(user));


        ratingService.rateMovie("The Avengers", 5.5, "token");

        verify(ratingRepository, atLeastOnce()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        OmdbResponse omdbResponse = OmdbResponse.builder()
                .boxOffice("$623,357,910")
                .title("The Avengers")
                .year("2012")
                .build();

        Movie movie = Movie.builder()
                .boxOffice(BigDecimal.valueOf(623357910))
                .year(2012)
                .title("The Avengers")
                .build();

        when(omdbClient.findMovieByTitle("The Avengers")).thenReturn(omdbResponse);
        when(movieService.saveMovieIfNotExist(Movie.createMovie(omdbResponse))).thenReturn(movie);
        when(userService.findUserByToken("token")).thenReturn(Optional.empty());

        Throwable exception = assertThrows(UserNotFoundException.class, () -> ratingService.rateMovie("The Avengers", 5.5, "token"));
        assertEquals("User not found", exception.getMessage());

        verify(ratingRepository, never()).save(any());
    }

    @Test
    void shouldGetTopTenMovies() {
        String email = "yildiz_serhat@hotmail.com";
        User user = User.builder()
                .token("token")
                .email(email)
                .build();

        when(userService.findUserByToken("token")).thenReturn(Optional.of(user));

        ratingService.getTopTenRatedMoviesOrderByBoxOffice("token");

        Pageable pageable = PageRequest.of(0, 10);
        verify(ratingRepository).getRatingByUserOrderByBoxOfficeValue(email, pageable);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundToGetTopMovies() {
        String email = "yildiz_serhat@hotmail.com";
        when(userService.findUserByToken("token")).thenReturn(Optional.empty());

        Throwable exception = assertThrows(UserNotFoundException.class, () -> ratingService.getTopTenRatedMoviesOrderByBoxOffice("token"));
        assertEquals("User not found", exception.getMessage());

        Pageable pageable = PageRequest.of(0, 10);
        verify(ratingRepository, never()).getRatingByUserOrderByBoxOfficeValue(email, pageable);
    }
}