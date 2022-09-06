package com.yildiz.serhat.movieservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopMoviesResponseModel {

    private String email;
    private String title;
    private double rating;
    private BigDecimal boxOffice;
}
