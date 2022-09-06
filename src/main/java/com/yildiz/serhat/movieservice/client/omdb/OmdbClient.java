package com.yildiz.serhat.movieservice.client.omdb;

import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import com.yildiz.serhat.movieservice.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class OmdbClient {
    private final OmdbAPI omdbAPI;
    @Value("${application.client.omdb-service.apiSecretKey}")
    private String apiSecretKey;

    public OmdbResponse findMovieByTitle(String title) {
        OmdbResponse omdbResponse = omdbAPI.getMovieInfo(apiSecretKey, title);
        validateResponse(omdbResponse);
        return omdbResponse;
    }

    private void validateResponse(OmdbResponse omdbResponse) {
        if (isNull(omdbResponse.getTitle())) {
            throw new MovieNotFoundException("Movie Not Found in OMDB", HttpStatus.NOT_FOUND);
        }
    }
}
