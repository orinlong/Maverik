package com.orin.maverik.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Data
@Entity(name="Movie")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    private String imdbID;
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String actors; // Comma separated list of actors
    private String plot;
    private String language;
    private String country;
    private String poster;
    private String imdbRating;
    private String owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return imdbID.equals(movie.imdbID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbID);
    }
}
