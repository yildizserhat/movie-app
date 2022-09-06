package com.yildiz.serhat.movieservice.domain.entity;

import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private int year;

    @Column(name = "box_office")
    private BigDecimal boxOffice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Rating> ratingList = new ArrayList<>();

    public static Movie createMovie(OmdbResponse omdbResponse) {
        return Movie.builder()
                .title(omdbResponse.getTitle())
                .year(Integer.parseInt(omdbResponse.getYear()))
                .boxOffice(new BigDecimal(omdbResponse.getBoxOffice()
                        .substring(1)
                        .replace(",", ""))).build();
    }
}
