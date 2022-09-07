package com.yildiz.serhat.movieservice.service.impl;

import com.yildiz.serhat.movieservice.client.omdb.OmdbClient;
import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import com.yildiz.serhat.movieservice.domain.entity.Movie;
import com.yildiz.serhat.movieservice.domain.enums.MovieAwardCategory;
import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import com.yildiz.serhat.movieservice.repository.MovieRepository;
import com.yildiz.serhat.movieservice.service.AcademyAwardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private AcademyAwardService academyAwardService;

    @Mock
    private OmdbClient omdbClient;

    @Mock
    private MovieRepository movieRepository;

    @Test
    void shouldFindBestPictureMovie() {
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

        AcademyAward academyAward = AcademyAward.builder()
                .year("2012")
                .won(false)
                .nominee("The Avengers")
                .build();

        when(omdbClient.findMovieByTitle("The Avengers")).thenReturn(omdbResponse);
        when(movieRepository.findMovieByTitle("The Avengers")).thenReturn(Optional.of(movie));
        when(academyAwardService.findAcademyAwardByNomineeAndCategory("The Avengers", MovieAwardCategory.BEST_PICTURE.getValue()))
                .thenReturn(Optional.of(academyAward));

        assertEquals(Optional.of(false), movieService.findBestPictureMovie("The Avengers"));
    }

    @Test
    void shouldSaveMovieIfNotExist() {
        Movie movie = Movie.builder()
                .boxOffice(BigDecimal.valueOf(623357910))
                .year(2012)
                .title("The Avengers")
                .build();

        when(movieRepository.findMovieByTitle("The Avengers")).thenReturn(Optional.empty());

        movieService.saveMovieIfNotExist(movie);

        verify(movieRepository, atLeastOnce()).save(movie);
    }

    @Test
    void shouldNotSaveMovieIfExist() {
        Movie movie = Movie.builder()
                .boxOffice(BigDecimal.valueOf(623357910))
                .year(2012)
                .title("The Avengers")
                .build();

        when(movieRepository.findMovieByTitle("The Avengers")).thenReturn(Optional.of(movie));

        movieService.saveMovieIfNotExist(movie);

        verify(movieRepository, never()).save(movie);
    }
}