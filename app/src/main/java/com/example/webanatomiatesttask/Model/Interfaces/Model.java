package com.example.webanatomiatesttask.Model.Interfaces;

import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.Data.MoviesResponse;
import java.util.List;
import retrofit2.Callback;

public interface Model {

  void getPopularMovies(Callback<MoviesResponse> instruction, int page);
  void saveMoviesToDb(List<Movie> movies);
  void loadMoviesFromDb();
}
