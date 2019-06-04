package com.example.webanatomiatesttask.Model.Network;

import com.example.webanatomiatesttask.Data.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepository {

    private ApiInterface TMDBApi;
    private Retrofit retrofitService;

    public NetworkRepository() {
      retrofitService = new Retrofit.Builder()
              .baseUrl("https://api.themoviedb.org/3/")
              .addConverterFactory(GsonConverterFactory.create())
              .build();
      TMDBApi = retrofitService.create(ApiInterface.class);
    }

    public void getPopularMovies (int page, Callback<MoviesResponse> instruction) {
        Call<MoviesResponse> call = TMDBApi.getPopularMovies(page);
        call.enqueue(instruction);
    }

}
