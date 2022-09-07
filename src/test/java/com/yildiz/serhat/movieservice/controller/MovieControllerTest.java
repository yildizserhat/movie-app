package com.yildiz.serhat.movieservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.yildiz.serhat.movieservice.TestInitializer;
import com.yildiz.serhat.movieservice.WireMockConfig;
import com.yildiz.serhat.movieservice.domain.entity.AcademyAward;
import com.yildiz.serhat.movieservice.domain.entity.Movie;
import com.yildiz.serhat.movieservice.domain.enums.MovieAwardCategory;
import com.yildiz.serhat.movieservice.domain.model.OmdbResponse;
import com.yildiz.serhat.movieservice.domain.model.UserResponseModel;
import com.yildiz.serhat.movieservice.repository.AcademyAwardRepository;
import com.yildiz.serhat.movieservice.repository.MovieRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(initializers = TestInitializer.class, classes = {WireMockConfig.class})
@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AcademyAwardRepository academyAwardRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${application.client.omdb-service.apiSecretKey}")
    private String apiSecretKey;

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
        wireMockServer.resetAll();
    }

    @Test
    @SneakyThrows
    void shouldFindBestPictureMovie() {
        saveAcademyAward();
        UserResponseModel register = registerUser();

        String url = "/v1/movies/best-picture/Avengers";
        mockOmdb();
        String token = register.getToken();
        mvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        List<Movie> all = movieRepository.findAll();

        assertEquals("Avengers", all.get(0).getTitle());
    }

    private UserResponseModel registerUser() {
        return userController.login("yildiz_serhat@hotmail.com");
    }

    private void saveAcademyAward() {
        AcademyAward academyAward = AcademyAward.builder()
                .nominee("Avengers")
                .won(true)
                .year("2012")
                .category(MovieAwardCategory.BEST_PICTURE.getValue())
                .build();

        academyAwardRepository.save(academyAward);
    }

    @SneakyThrows
    private void mockOmdb() {
        String movieTitle = "Avengers";
        OmdbResponse omdbResponse = OmdbResponse.builder()
                .boxOffice("$623,357,910")
                .title(movieTitle)
                .year("2012")
                .build();

        wireMockServer.stubFor(WireMock.get("/?apikey=" + apiSecretKey + "&t=" + movieTitle)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(omdbResponse))));
    }

}