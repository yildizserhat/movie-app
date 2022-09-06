package com.yildiz.serhat.movieservice.repository;

import com.yildiz.serhat.movieservice.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findMovieByTitle(String title);

}
