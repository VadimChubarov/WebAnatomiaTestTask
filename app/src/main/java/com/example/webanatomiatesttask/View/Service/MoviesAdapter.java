package com.example.webanatomiatesttask.View.Service;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.webanatomiatesttask.SupportUtils.Constants;
import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.Data.MoviesResponse;
import com.example.webanatomiatesttask.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.RecyclerViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int moviesPage;
    private int lastPage;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getMoviesPage() {
        return moviesPage;
    }

    public void setMoviesPage(int moviesPage) {
        this.moviesPage = moviesPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void updateItems(Collection<Movie> items) {
        clearItems();
        moviesPage = 0;
        movieList.addAll(items);
        notifyDataSetChanged();
    }

    public void updateItems(MoviesResponse items) {
        clearItems();
        moviesPage = items.getPage();
        lastPage = items.getTotalPages();
        movieList.addAll(items.getMovies());
        notifyDataSetChanged();
    }

    public void addItems(MoviesResponse items) {
        moviesPage = items.getPage();
        lastPage = items.getTotalPages();
        movieList.addAll(items.getMovies());
        notifyItemRangeInserted(movieList.size(),items.getMovies().size());
    }

    public void clearItems(){
        moviesPage = 1;
        movieList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_movies_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(movieList.get(position));
        if(onItemClickListener!=null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(movieList.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_movie_title) TextView title;
        @BindView(R.id.iv_movie_picture) ImageView picture;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Movie movie) {
           title.setText(movie.getTitle());
           String pictureURL = String.format("%s%s%s",
                   Constants.getImagesBaseUrl(),
                   Constants.getImagesSizeSmall(),
                   movie.getBackdropPath());
           Picasso.with(itemView.getContext())
                    .load(pictureURL)
                    .into(picture);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Movie movie);
    }
}
