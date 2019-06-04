package com.example.webanatomiatesttask.View.Interfaces;

import android.content.Context;
import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.Data.MoviesResponse;
import java.util.List;

public interface View {

    Context getAppContext();
    void showMovies(MoviesResponse movies);
    void refreshMovies(MoviesResponse movies);
    void showMoviesFromDb(List<Movie> movies);
    void showMovieDetailsScreen(Movie movie);
    void showNetworkFailure();
    boolean isMoviesDisplayed();
    void showLoading(boolean show);
}
