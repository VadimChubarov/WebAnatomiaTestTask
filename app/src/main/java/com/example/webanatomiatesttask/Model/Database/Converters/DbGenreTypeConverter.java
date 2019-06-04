package com.example.webanatomiatesttask.Model.Database.Converters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;


public class DbGenreTypeConverter {

    Gson gson = new Gson();

    @TypeConverter
    public String fromGenreId(List<Integer> genreId ){
        return gson.toJson(genreId);
    }

    @TypeConverter
    public List<Integer> toGenreId(String genreIds){
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(genreIds, listType);
    }
}
