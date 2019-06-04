package com.example.webanatomiatesttask.Model.Network;

import com.example.webanatomiatesttask.BuildConfig;
import com.example.webanatomiatesttask.Data.MoviesResponse;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface ApiInterface {

    String ApiKey =  BuildConfig.ak;
    String getPopular = "movie/popular";

    @GET(getPopular+"?api_key="+ApiKey)
    Call<MoviesResponse>getPopularMovies(@Query("page") Integer page);
}
