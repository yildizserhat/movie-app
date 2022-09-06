package com.yildiz.serhat.movieservice.controller;

import com.yildiz.serhat.movieservice.domain.model.TopMoviesResponseModel;
import com.yildiz.serhat.movieservice.repository.UserRepository;
import com.yildiz.serhat.movieservice.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/movie-rating")
@RequiredArgsConstructor
@Tag(name = "movie", description = "Endpoints about movie rating")
public class RatingController {

    private final UserRepository repository;

    private final RatingService ratingService;

    @GetMapping("/{title}/{rate}")
    @Operation(summary = "Rate Movie By User")
    public ResponseEntity<?> rateMovieByUser(@PathVariable("title") String title,
                                             @PathVariable("rate") double rate,
                                             @RequestHeader(name = "Authorization") String token) {
        if (StringUtils.isBlank(token)) {
            return ResponseEntity.badRequest().build();
        }
        ratingService.rateMovie(title, rate, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top-ten-rated-movies")
    public ResponseEntity<?> getTopTenRatedMoviesByUser(@RequestHeader(name = "Authorization") String token) {
        if (StringUtils.isBlank(token)) {
            return ResponseEntity.badRequest().build();
        }
        List<TopMoviesResponseModel> topTenRatedMoviesOrderByBoxOffice = ratingService.getTopTenRatedMoviesOrderByBoxOffice(token);
        return ResponseEntity.ok(topTenRatedMoviesOrderByBoxOffice);
    }
}
