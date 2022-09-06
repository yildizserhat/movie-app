package com.yildiz.serhat.movieservice.repository;

import com.yildiz.serhat.movieservice.domain.entity.Rating;
import com.yildiz.serhat.movieservice.domain.model.TopMoviesResponseModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "SELECT new com.yildiz.serhat.movieservice.domain.model.TopMoviesResponseModel(u.email, m.title, r.rating, m.boxOffice) " +
            "from Rating r,User u, Movie m where  r.user=u and r.movie= m and u.email=:email order by m.boxOffice desc")
    List<TopMoviesResponseModel> getRatingByUserOrderByBoxOfficeValue(@RequestParam String email, Pageable pageable);
}

