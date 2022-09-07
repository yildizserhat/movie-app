package com.yildiz.serhat.movieservice.service;

import com.yildiz.serhat.movieservice.domain.entity.Movie;

import java.util.Optional;

public interface MovieService {

    Optional<Boolean> findBestPictureMovie(String title);

    Movie saveMovieIfNotExist(Movie movie);
}
