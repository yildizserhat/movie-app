package com.yildiz.serhat.movieservice.service;

import com.yildiz.serhat.movieservice.domain.model.TopMoviesResponseModel;

import java.util.List;

public interface RatingService {
    void rateMovie(String title, double rate, String token);

    List<TopMoviesResponseModel> getTopTenRatedMoviesOrderByBoxOffice(String token);
}
