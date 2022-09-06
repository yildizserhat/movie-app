package com.yildiz.serhat.movieservice.service.impl;

import com.yildiz.serhat.movieservice.client.omdb.OmdbClient;
import com.yildiz.serhat.movieservice.domain.entity.Movie;
import com.yildiz.serhat.movieservice.domain.entity.Rating;
import com.yildiz.serhat.movieservice.domain.entity.User;
import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import com.yildiz.serhat.movieservice.domain.model.TopMoviesResponseModel;
import com.yildiz.serhat.movieservice.exception.UserNotFoundException;
import com.yildiz.serhat.movieservice.repository.RatingRepository;
import com.yildiz.serhat.movieservice.service.MovieService;
import com.yildiz.serhat.movieservice.service.RatingService;
import com.yildiz.serhat.movieservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yildiz.serhat.movieservice.domain.entity.Movie.createMovie;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final OmdbClient omdbClient;
    private final MovieService movieService;
    private final UserService userService;

    @Override
    public void rateMovie(String title, double rate, String token) {
        OmdbResponse omdbResponse = omdbClient.findMovieByTitle(title);
        Movie movie = movieService.saveMovieIfNotExist(createMovie(omdbResponse));
        Optional<User> user = userService.findUserByToken(prepareToken(token));

        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        Rating rating = Rating.buildRate(rate, user.get(), movie);
        ratingRepository.save(rating);
    }

    @Override
    public List<TopMoviesResponseModel> getTopTenRatedMoviesOrderByBoxOffice(String token) {
        Optional<User> userByToken = userService.findUserByToken(prepareToken(token));
        if (!userByToken.isPresent()) {
            throw new UserNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        return ratingRepository.getRatingByUserOrderByBoxOfficeValue(userByToken.get().getEmail(), getPagination());
    }

    private PageRequest getPagination() {
        return PageRequest.of(0, 10);
    }

    private String prepareToken(String token) {
        return token.replace("Bearer ", "").strip();
    }
}
