package com.yildiz.serhat.movieservice.client.omdb;

import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OmdbFeignClientConfig {

    @Bean("omdbErrorDecoder")
    public ErrorDecoder orderErrorDecoder() {
        return new OmdbErrorDecoder();
    }

}


