package com.yildiz.serhat.movieservice.repository;

import com.yildiz.serhat.movieservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByToken(String token);
}
