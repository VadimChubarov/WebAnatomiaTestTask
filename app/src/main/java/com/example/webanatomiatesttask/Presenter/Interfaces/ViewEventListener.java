package com.example.webanatomiatesttask.Presenter.Interfaces;

import com.example.webanatomiatesttask.Data.Movie;

public interface ViewEventListener {

    void onViewRefreshed();
    void onPaginationMovies(int page);
    void onMovieSelected(Movie movie);
}
