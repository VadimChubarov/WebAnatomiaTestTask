package com.example.webanatomiatesttask.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.webanatomiatesttask.SupportUtils.Constants;
import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.R;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_movie_poster) ImageView ivPoster;
    @BindView(R.id.tv_movie_title) TextView tvTitle;
    @BindView(R.id.tv_popularity) TextView tvPopularity;
    @BindView(R.id.tv_release_date) TextView tvRelease;
    @BindView(R.id.tv_movie_overview) TextView tvOverview;

    private Unbinder unbinder;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie_details);
        unbinder = ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        movie = (Movie)getIntent().getSerializableExtra(Constants.getMovieDataCode());

        String pictureURL = String.format("%s%s%s",
                Constants.getImagesBaseUrl(),
                Constants.getImagesSizeLarge(),
                movie.getPosterPath());
        Picasso.with(this)
                .load(pictureURL)
                .into(ivPoster);

        tvTitle.setText(movie.getTitle());
        tvPopularity.setText(String.valueOf(movie.getPopularity()));
        tvRelease.setText(movie.getReleaseDate());
        tvOverview.setText(movie.getOverview());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
