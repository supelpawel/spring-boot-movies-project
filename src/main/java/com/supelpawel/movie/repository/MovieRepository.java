package com.supelpawel.movie.repository;

import com.supelpawel.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  @Query(value = "SELECT m FROM Movie m ORDER BY m.users.size DESC")
  List<Movie> findTopMovies();

  Movie findMovieByTitle(String title);
}