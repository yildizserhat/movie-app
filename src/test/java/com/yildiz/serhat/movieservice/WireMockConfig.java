package com.yildiz.serhat.movieservice;


import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@TestConfiguration
public class WireMockConfig {

    @Bean(initMethod = "start",destroyMethod = "stop")
    public WireMockServer mockMovieService(){
        return new WireMockServer(9562);
    }
}
