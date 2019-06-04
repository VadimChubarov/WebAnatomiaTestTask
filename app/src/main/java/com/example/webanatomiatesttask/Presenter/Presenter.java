package com.example.webanatomiatesttask.Presenter;

import com.example.webanatomiatesttask.Model.Interfaces.Model;
import com.example.webanatomiatesttask.Model.Repository;
import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.Data.MoviesResponse;
import com.example.webanatomiatesttask.Presenter.Interfaces.ModelEventListener;
import com.example.webanatomiatesttask.Presenter.Interfaces.ViewEventListener;
import com.example.webanatomiatesttask.View.Interfaces.View;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter implements ViewEventListener, ModelEventListener {

    private View view;
    private Model model;
    private static Presenter presenter;

    private Presenter (View view) {
            this.model = new Repository(view.getAppContext(),this);
            this.view = view;
            presenter = this;
        }

    public void setView(View view) {
        this.view = view;
    }

    public static Presenter getInstance(View view) {
        if(presenter==null) return new Presenter(view);
        else {
            presenter.setView(view);
            return presenter;
        }
    }

    @Override
    public void onViewRefreshed() {
       model.loadMoviesFromDb();
       loadMoviesFromNetwork(1);
    }

    @Override
    public void onPaginationMovies(int page) {
        loadMoviesFromNetwork(page);
    }

    private void loadMoviesFromNetwork(int page){
        view.showLoading(true);
        model.getPopularMovies(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(response.isSuccessful()) {
                    if(page==1){
                        view.refreshMovies(response.body());
                    }else {
                        view.showMovies(response.body());
                    }
                    model.saveMoviesToDb(response.body().getMovies());
                    view.showLoading(false);
                }
                else{
                    view.showLoading(false);
                    view.showNetworkFailure();}
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                view.showLoading(false);
                view.showNetworkFailure();
            }
        },page);
    }

    @Override
    public void onLoadMoviesFromDb(List<Movie> movies) {
        if(movies.size() > 0 && !view.isMoviesDisplayed()){
           view.showMoviesFromDb(movies);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {
        view.showMovieDetailsScreen(movie);
    }
}
