package com.forest.api.composite.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class MovieAggregate {
    private int movieId;
    private String name;
    private String director;
    private String theatreReleaseDate;
    private List<ReviewSummary> review;
    private ServiceAddress serviceAddress;

    public <T> MovieAggregate(int movieIdOkay, String s, String s1, String s2, List<T> singletonList, Object o) {
    }
}
