package com.supelpawel.movie.dto;

import com.supelpawel.movie.model.Movie;
import com.supelpawel.user.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String plot;
  private String genre;
  private String director;
  private int year;
  private String poster;
  @ManyToMany(fetch = FetchType.EAGER)
  private List<User> users = new ArrayList<>();

  public static MovieDto from(MovieResponseDto movieResponseDto) {
    MovieDto movieDto = new MovieDto();

    movieDto.setTitle(movieResponseDto.getTitle());
    movieDto.setPlot(movieResponseDto.getPlot());
    movieDto.setGenre(movieResponseDto.getGenre());
    movieDto.setDirector(movieResponseDto.getDirector());
    movieDto.setYear(movieResponseDto.getYear());
    movieDto.setPoster(movieResponseDto.getPoster());
    return movieDto;
  }

  public static MovieDto from(Movie movie) {
    MovieDto movieDto = new MovieDto();

    movieDto.setId(movie.getId());
    movieDto.setTitle(movie.getTitle());
    movieDto.setPlot(movie.getPlot());
    movieDto.setGenre(movie.getGenre());
    movieDto.setDirector(movie.getDirector());
    movieDto.setYear(movie.getYear());
    movieDto.setPoster(movie.getPoster());
    return movieDto;
  }
}