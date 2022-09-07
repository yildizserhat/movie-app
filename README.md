
# Movie Application

The application should indicate whether a movie won a “Best Picture” Oscar, given a movie’s title based on this API and the CSV file that contains winners from 1927 until 2010. It should also allow users to give a rating to movies and provide a list of 10 top-rated movies ordered by box office value.

## API Reference

#### User must be register to use the other endpoints.

```http
  GET /login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email` | `string` | **Required**.  |


#### Indicate whether the movie wins Best Picture or not.

```http
  GET /v1/movies/best-picture/{title}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `title` | `string` | **Required**.  |

#### User can rate any movie.

```http
  GET /v1/movie-rating/{title}/{rate}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `title` | `string` | **Required**.  |
| `rate` | `double` | **Required**.  |

#### Get top ten rated movies by user

```http
  GET /v1/movie-rating/top-ten-rated-movies
```

#### Documentation 

```http
  localhost:8080/documentation 
```



## Tech Stack

**Technologies:** Java 17, Spring Boot, MySQL database, Docker, TestContainer, Junit, Mockito, Integration Test, Feign Client, Spring Security

