package com.example.webanatomiatesttask.Model;

import android.content.Context;
import com.example.webanatomiatesttask.Model.Database.DataBaseRepository;
import com.example.webanatomiatesttask.Model.Interfaces.Model;
import com.example.webanatomiatesttask.Model.Network.NetworkRepository;
import com.example.webanatomiatesttask.Presenter.Interfaces.ModelEventListener;
import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.Data.MoviesResponse;
import java.util.List;
import retrofit2.Callback;

public class Repository implements Model {


    private NetworkRepository networkRepository;
    private DataBaseRepository dataBaseRepository;
    private ModelEventListener modelEventListener;


    public Repository(Context context, ModelEventListener modelEventListener) {
        this.modelEventListener = modelEventListener;
        this.networkRepository = new NetworkRepository();
        this.dataBaseRepository = new DataBaseRepository(context,modelEventListener);
    }

    @Override
    public void getPopularMovies(Callback<MoviesResponse> instruction, int page) {
       networkRepository.getPopularMovies(page,instruction);
    }

    @Override
    public void saveMoviesToDb(List<Movie> movies) {
       dataBaseRepository.saveMoviesToDb(movies);
    }

    @Override
    public void loadMoviesFromDb() {
        dataBaseRepository.loadMoviesFromDb();
    }
}


