package com.supelpawel.movie.service;

import com.supelpawel.movie.dto.MovieDto;
import com.supelpawel.movie.model.Movie;
import com.supelpawel.movie.dto.MovieResponseDto;
import com.supelpawel.movie.repository.MovieRepository;
import com.supelpawel.user.model.CurrentUser;
import com.supelpawel.user.model.User;
import com.supelpawel.user.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Data
@AllArgsConstructor
public class OmdbMovieService implements MovieService {

  public static final int NUMBER_OF_TOP_MOVIES = 3;
  public static final String OMDB_API_KEY = "b1d18668";
  private final MovieRepository movieRepository;
  private final UserService userService;
  private final WebClient client;

  @Override
  public MovieResponseDto findByTitleAndYearOfRelease(String title, int year) {
    RequestBodyUriSpec uriSpec = client.method(HttpMethod.GET);

    uriSpec.uri(uriBuilder -> uriBuilder.path("/")
        .queryParam("apikey", OMDB_API_KEY)
        .queryParam("t", title)
        .queryParam("y", year)
        .build()
    );
    Mono<MovieResponseDto> movieResponseDto = uriSpec.exchangeToMono(
        response -> response.bodyToMono(MovieResponseDto.class));
    MovieResponseDto returnMovie = movieResponseDto.block();

    Optional.ofNullable(Objects.requireNonNull(returnMovie).getTitle())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    return returnMovie;
  }

  @Override
  public String processSearchMovieForm(String title, int year, Model model) {
    MovieResponseDto movieResponseDto = findByTitleAndYearOfRelease(title, year);
    MovieDto movieDto = MovieDto.from(movieResponseDto);

    model.addAttribute("movie", movieDto);
    return "movie/searched";
  }

  @Override
  @Transactional
  public String addMovieToFavouriteList(String title, int year, CurrentUser currentUser,
      Model model) {
    User user = currentUser.getUser();
    List<Movie> movieList = user.getFavouriteMovies();
    MovieResponseDto movieResponseDto;
    Movie movie;

    if (movieList.stream()
        .anyMatch(m -> title.equals(m.getTitle()))) {
      return "movie/warning";
    }

    if (movieRepository.findMovieByTitle(title) == null) {
      movieResponseDto = findByTitleAndYearOfRelease(title, year);
      movie = Movie.from(movieResponseDto);
    } else {
      movie = findMovieByTitle(title);
    }
    movie.getUsers().add(user);
    movieRepository.save(movie);
    user.getFavouriteMovies().add(movie);
    userService.update(user);

    movieList = user.getFavouriteMovies();
    List<MovieDto> movieDtoList = movieList.stream()
        .map(MovieDto::from)
        .collect(Collectors.toList());
    model.addAttribute("movies", movieDtoList);
    return "movie/favourite";
  }

  @Override
  public String showFavouriteMovieList(CurrentUser currentUser, Model model) {
    User user = currentUser.getUser();
    List<Movie> movieList = user.getFavouriteMovies();
    List<MovieDto> movieDtoList = movieList.stream()
        .map(MovieDto::from)
        .collect(Collectors.toList());

    model.addAttribute("movies", movieDtoList);
    return "movie/favourite";
  }

  @Override
  public List<Movie> findByUserId(long id) {
    return findByUserId(id);
  }

  @Override
  public String findTop3FavouriteMovies(Model model) {
    List<MovieDto> topMovies = movieRepository.findTopMovies().stream()
        .map(MovieDto::from)
        .collect(Collectors.toList());

    if (topMovies.size() > NUMBER_OF_TOP_MOVIES) {
      topMovies = topMovies.subList(0, NUMBER_OF_TOP_MOVIES);
    }
    model.addAttribute("topMovies", topMovies);
    return "movie/top3";
  }

  @Override
  @Transactional
  public String deleteMovieFromFavouriteList(CurrentUser currentUser, int id) {
    User user = currentUser.getUser();
    List<Movie> movies = user.getFavouriteMovies();
    Optional<Movie> optMovieToDelete = movies.stream().
        filter(m -> m.getId() == id).findFirst();

    if (optMovieToDelete.isPresent()) {
      Movie movieToDelete = optMovieToDelete.get();
      user.getFavouriteMovies().remove(movieToDelete);
      userService.update(user);
      movieToDelete.getUsers().remove(user);

      if (movieToDelete.getUsers().isEmpty()) {
        movieRepository.delete(movieToDelete);
      } else {
        movieRepository.save(movieToDelete);
      }
    }
    return "redirect:/movie/favourite-list";
  }

  public Movie findMovieByTitle(String title) {
    return movieRepository.findMovieByTitle(title);
  }
}