package com.example.webanatomiatesttask.Model.Database;

import android.content.Context;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.webanatomiatesttask.Data.Movie;
import com.example.webanatomiatesttask.Model.Database.Converters.DbGenreTypeConverter;
import java.util.List;


@Database(entities = {Movie.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase appDataBase;
    public abstract MoviesDao moviesDao();

    public static synchronized AppDataBase getDatabase(Context context){
        if(appDataBase == null){
            appDataBase = Room.databaseBuilder(context,
                    AppDataBase.class, "AppDatabase").build();
        }
        return appDataBase;
    }

    @Dao
    @TypeConverters(DbGenreTypeConverter.class)
    public interface MoviesDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertMovies(Movie... movies);

        @Query("DELETE FROM movies_table WHERE id = :movieId")
        void deleteItem(int movieId);

        @Query("DELETE FROM movies_table")
        void deleteAllItems();

        @Query("SELECT * from movies_table ORDER BY popularity DESC")
        List<Movie> getAllItems();

        @Query("SELECT * FROM movies_table WHERE id = :movieId ")
        Movie getItem(int movieId);
    }
}
