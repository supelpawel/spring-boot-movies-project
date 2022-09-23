package com.supelpawel.movie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Movie {

  private String title;
  private int year;
  private String rated;
  private String released;
  private String runtime;
  private String genre;
  private String director;
  private String writer;
  private String actors;
  private String plot;
  private String language;
  private String country;
  private String awards;
  private String poster;
  @OneToMany
  private List<Rating> ratings = new ArrayList<>();
  private String metaScore;
  private String imdbRating;
  private String imdbVotes;
  private String imbdId;
  private String type;
  private String dvd;
  private String production;
  private String website;
  private String response;
}
