package com.supelpawel.movie.service;

import com.supelpawel.movie.dto.MovieResponseDto;
import com.supelpawel.movie.model.Movie;
import com.supelpawel.user.model.CurrentUser;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Repository
public interface MovieService {

  MovieResponseDto findByTitleAndYearOfRelease(String title, int year)
      throws IOException, InterruptedException;

  String processSearchMovieForm(String title, int year, Model model)
      throws IOException, InterruptedException;

  String addMovieToFavouriteList(String title, int year, CurrentUser currentUser, Model model)
      throws IOException, InterruptedException;

  String showFavouriteMovieList(CurrentUser currentUser, Model model);

  List<Movie> findByUserId(long id);

  String findTop3FavouriteMovies(Model model) throws IOException, InterruptedException;

  String deleteMovieFromFavouriteList(CurrentUser currentUser, int id);
}