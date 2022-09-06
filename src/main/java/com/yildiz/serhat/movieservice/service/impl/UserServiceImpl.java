package com.yildiz.serhat.movieservice.service.impl;

import com.yildiz.serhat.movieservice.domain.entity.User;
import com.yildiz.serhat.movieservice.domain.model.UserResponseModel;
import com.yildiz.serhat.movieservice.repository.UserRepository;
import com.yildiz.serhat.movieservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseModel registerUser(String email) {
        String token = getJWTToken(email);
        userRepository.save(User.builder()
                .token(token)
                .email(email)
                .build());
        return UserResponseModel.builder()
                .email(email)
                .token(token)
                .build();
    }

    @Override
    public Optional<User> findUserByToken(String token) {
        return userRepository.findUserByToken(token);
    }

    private String getJWTToken(String email) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("movieAppJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}
