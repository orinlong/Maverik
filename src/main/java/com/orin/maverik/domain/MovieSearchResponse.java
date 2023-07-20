package com.orin.maverik.domain;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieSearchResponse {
    private List<Movie> movieList = new ArrayList<>();

    public MovieSearchResponse(List<Movie> movies) {
        this.movieList.addAll(movies);
    }
}
