package com.example.webanatomiatesttask.SupportUtils;

public class Constants {
    private final static String imagesBaseUrl = "https://image.tmdb.org/t/p/";
    private final static String imagesSizeSmall = "w300";
    private final static String imagesSizeMedium = "w500";
    private final static String imagesSizeLarge = "w780";
    private final static String imagesSizeOrigin = "original";

    private final static String MOVIE_DATA = "movie_data";
    private final static String CURRENT_PAGE = "current_page";
    private final static String LAST_PAGE = "last_page";
    private final static String CURRENT_MOVIES = "current_movies";

    public static String getImagesBaseUrl() {
        return imagesBaseUrl;
    }

    public static String getImagesSizeSmall() {
        return imagesSizeSmall;
    }

    public static String getImagesSizeMedium() {
        return imagesSizeMedium;
    }

    public static String getImagesSizeLarge() {
        return imagesSizeLarge;
    }

    public static String getImagesSizeOrigin() {
        return imagesSizeOrigin;
    }

    public static String getMovieDataCode() {
        return MOVIE_DATA;
    }

    public static String getCurrentPage() {
        return CURRENT_PAGE;
    }

    public static String getLastPage() {
        return LAST_PAGE;
    }

    public static String getCurrentMovies() {
        return CURRENT_MOVIES;
    }
}
