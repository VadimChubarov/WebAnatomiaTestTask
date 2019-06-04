package com.example.webanatomiatesttask.Model.Database;

import android.content.Context;
import android.os.Handler;
import com.example.webanatomiatesttask.Presenter.Interfaces.ModelEventListener;
import com.example.webanatomiatesttask.Data.Movie;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class DataBaseRepository {

   private AppDataBase dataBase;
   private ModelEventListener modelEventListener;
   private final Executor executor;
   private Handler dbHandler;

    public DataBaseRepository(Context context, ModelEventListener modelEventListener) {
        this.dataBase = AppDataBase.getDatabase(context);
        this.modelEventListener = modelEventListener;
        this.executor = Executors.newFixedThreadPool(2);
        this.dbHandler = new Handler();
    }

    public void saveMoviesToDb(List<Movie> movies){
            executor.execute(() -> {
                Movie[] moviesToDb = new Movie[movies.size()];
                for(int i = 0; i < movies.size(); i++ ){
                    moviesToDb[i] = movies.get(i);
                }
                dataBase.moviesDao().deleteAllItems();
                dataBase.moviesDao().insertMovies(moviesToDb);
            });
        }

    public void loadMoviesFromDb(){
        executor.execute(() -> {
            List<Movie> movies = dataBase.moviesDao().getAllItems();
            dbHandler.post(() -> { modelEventListener.onLoadMoviesFromDb(movies); });
        });
    }
}

