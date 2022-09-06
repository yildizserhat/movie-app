package com.yildiz.serhat.movieservice.service;

import com.yildiz.serhat.movieservice.domain.entity.User;
import com.yildiz.serhat.movieservice.domain.model.UserResponseModel;

import java.util.Optional;

public interface UserService {

    UserResponseModel registerUser(String email);

    Optional<User> findUserByToken(String token);
}
