package com.yildiz.serhat.movieservice.service.impl;

import com.yildiz.serhat.movieservice.client.omdb.OmdbClient;
import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import com.yildiz.serhat.movieservice.domain.entity.Movie;
import com.yildiz.serhat.movieservice.domain.enums.MovieAwardCategory;
import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import com.yildiz.serhat.movieservice.repository.MovieRepository;
import com.yildiz.serhat.movieservice.service.AcademyAwardService;
import com.yildiz.serhat.movieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.yildiz.serhat.movieservice.domain.entity.Movie.createMovie;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final OmdbClient omdbClient;
    private final AcademyAwardService academyAwardService;
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public Optional<Boolean> findBestPictureMovie(String title) {
        OmdbResponse omdbResponse = omdbClient.findMovieByTitle(title);
        saveMovieIfNotExist(createMovie(omdbResponse));

        Optional<AcademyAward> academyAwardByNominee = academyAwardService.findAcademyAwardByNomineeAndCategory(omdbResponse.getTitle(), MovieAwardCategory.BEST_PICTURE.getValue());
        return academyAwardByNominee.stream()
                .filter(academyAward -> academyAward.getNominee().equalsIgnoreCase(title))
                .map(AcademyAward::isWon).findAny();
    }

    public Movie saveMovieIfNotExist(Movie movie) {
        Optional<Movie> movieByTitle = movieRepository.findMovieByTitle(movie.getTitle());
        if (!movieByTitle.isPresent()) {
            log.info("Movie will be saved: {}", movie.getTitle());
            return movieRepository.save(movie);
        }
        log.info("Movie already in DB: {}", movie.getTitle());
        return movieByTitle.get();
    }
}
