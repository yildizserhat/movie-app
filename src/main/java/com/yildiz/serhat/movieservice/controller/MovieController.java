package com.yildiz.serhat.movieservice.controller;

import com.yildiz.serhat.movieservice.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/movies")
@RequiredArgsConstructor
@Tag(name = "movie", description = "Endpoints about movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/best-picture/{title}")
    @Operation(summary = "Find best-picture movie based on omdb-api and csv file")
    public ResponseEntity<?> findBestPictureMovie(@PathVariable("title") String title) {
        Optional<Boolean> movie = movieService.findBestPictureMovie(title);
        return movie.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
