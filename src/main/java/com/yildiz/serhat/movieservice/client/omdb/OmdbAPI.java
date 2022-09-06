package com.yildiz.serhat.movieservice.client.omdb;


import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "omdb-service", url = "${application.client.omdb-service.url}", configuration = OmdbFeignClientConfig.class)
public interface OmdbAPI {

    @GetMapping(path = "?apikey={apikey}&t={title}", consumes = MediaType.APPLICATION_JSON_VALUE)
    OmdbResponse getMovieInfo(@PathVariable String apikey,@PathVariable String title);
}

