package com.supelpawel.movie.controller;

import com.supelpawel.movie.service.MovieService;
import com.supelpawel.user.model.CurrentUser;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping("/movie/search")
  public String showSearchMovieForm() {
    return "movie/search";
  }

  @PostMapping("/movie/search")
  public String processSearchMovieForm(@RequestParam String title, @RequestParam int year,
      Model model) throws IOException, InterruptedException {
    return movieService.processSearchMovieForm(title, year, model);
  }

  @PostMapping("/movie/favourite")
  String addMovieToFavouriteList(@RequestParam String title, @RequestParam int year,
      @AuthenticationPrincipal CurrentUser currentUser,
      Model model) throws IOException, InterruptedException {
    return movieService.addMovieToFavouriteList(title, year, currentUser, model);
  }

  @GetMapping("/movie/favourite-list")
  String showFavouriteMovieList(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
    return movieService.showFavouriteMovieList(currentUser, model);
  }

  @PostMapping("/movie/delete")
  String deleteMovieFromFavouriteList(@AuthenticationPrincipal CurrentUser currentUser,
      @RequestParam int id) {
    return movieService.deleteMovieFromFavouriteList(currentUser, id);
  }

  @GetMapping("/movie/top3")
  String showTop3FavouriteMovies(Model model) throws IOException, InterruptedException {
    return movieService.findTop3FavouriteMovies(model);
  }
}