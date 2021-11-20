package com.forest.api.core.Movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Movie {

    private  int movieId;
    private  String name;
    private  String director;
    private  String theatreReleaseDate;
    private  String serviceAddress;

    public Movie(int movieId, String name, String director, String theatreReleaseDate, Object o) {
    }
}
