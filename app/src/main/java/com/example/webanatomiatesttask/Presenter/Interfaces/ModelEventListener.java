package com.example.webanatomiatesttask.Presenter.Interfaces;

import com.example.webanatomiatesttask.Data.Movie;
import java.util.List;

public interface ModelEventListener {

   void onLoadMoviesFromDb(List<Movie> movies);
}
