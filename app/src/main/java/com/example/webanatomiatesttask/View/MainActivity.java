package com.example.webanatomiatesttask.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.webanatomiatesttask.SupportUtils.Constants;
import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.Data.MoviesResponse;
import com.example.webanatomiatesttask.Presenter.Presenter;
import com.example.webanatomiatesttask.R;
import com.example.webanatomiatesttask.Presenter.Interfaces.ViewEventListener;
import com.example.webanatomiatesttask.View.Interfaces.View;
import com.example.webanatomiatesttask.View.Service.MoviesAdapter;
import java.io.Serializable;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements View {

    @BindView(R.id.vg_movies_container) SwipeRefreshLayout vgMoviesContainer;
    @BindView(R.id.rv_movies) RecyclerView rvMovies;
    @BindView(R.id.pb_loading) ProgressBar pbLoading;

    private Unbinder unbinder;
    private ViewEventListener viewEventListener;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setUpMoviesRecycler();
        notifyPresenter(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(moviesAdapter.getItemCount()>0)
            outState.putInt(Constants.getCurrentPage(), moviesAdapter.getMoviesPage());
            outState.putInt(Constants.getLastPage(), moviesAdapter.getLastPage());
            outState.putSerializable(Constants.getCurrentMovies(), (Serializable) moviesAdapter.getMovieList());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            List<Movie> movies = (List<Movie>) savedInstanceState.getSerializable(Constants.getCurrentMovies());
            int page = savedInstanceState.getInt(Constants.getCurrentPage());
            int lastPage = savedInstanceState.getInt(Constants.getLastPage());
            if(movies!=null){
               moviesAdapter.updateItems(movies);
               moviesAdapter.setMoviesPage(page);
               moviesAdapter.setLastPage(lastPage);
            }
        }catch(Exception e){e.printStackTrace();}
    }

    private void notifyPresenter(Bundle state){
        viewEventListener = Presenter.getInstance(this);
        if(state==null)viewEventListener.onViewRefreshed();
    }

    private void setUpMoviesRecycler(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManager);
        moviesAdapter = new MoviesAdapter();
        moviesAdapter.setOnItemClickListener(movie -> viewEventListener.onMovieSelected(movie));
        rvMovies.setAdapter(moviesAdapter);
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItems) >= totalItemCount && !isMoviesLoading()) {
                        if(moviesAdapter.getMoviesPage() == 0){
                            viewEventListener.onViewRefreshed();
                        }else if(moviesAdapter.getMoviesPage() < moviesAdapter.getLastPage()){
                            viewEventListener.onPaginationMovies(moviesAdapter.getMoviesPage() + 1);
                        }
                    }
                }
            }
        });
        vgMoviesContainer.setOnRefreshListener(() ->
               viewEventListener.onViewRefreshed());
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void showMovies(MoviesResponse movies) {
       moviesAdapter.addItems(movies);
    }

    @Override
    public void showMoviesFromDb(List<Movie> movies) {
        moviesAdapter.updateItems(movies);
    }

    @Override
    public void refreshMovies(MoviesResponse movies) {
        vgMoviesContainer.setRefreshing(false);
        rvMovies.smoothScrollToPosition(0);
        moviesAdapter.updateItems(movies);
    }

    @Override
    public void showMovieDetailsScreen(Movie movie) {
        Intent intent = new Intent(this,MovieDetailsActivity.class);
        intent.putExtra(Constants.getMovieDataCode(),movie);
        startActivity(intent);
    }

    @Override
    public void showNetworkFailure() {
        vgMoviesContainer.setRefreshing(false);
        Toast.makeText(getApplicationContext(), "Internet connection problem", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isMoviesDisplayed() {
        return rvMovies.getChildCount() > 0;
    }

    @Override
    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? android.view.View.VISIBLE: android.view.View.GONE);
    }

    private boolean isMoviesLoading(){
        return pbLoading.getVisibility()== android.view.View.VISIBLE;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        viewEventListener = null;
    }
}
