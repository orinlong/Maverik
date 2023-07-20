package com.orin.maverik.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message="ImdbID is required")
    private String imdbID;
    @NotBlank(message="Title is required")
    private String title;
    @NotBlank(message="Year is required")
    private String year;
    @NotBlank(message="Rated is required")
    private String rated;
    @NotBlank(message="Released is required")
    private String released;
    @NotBlank(message="Runtime is required")
    private String runtime;
    @NotBlank(message="Genre is required")
    private String genre;
    @NotBlank(message="Director is required")
    private String director;
    @NotBlank(message="Actors field is required")
    private String actors; // Comma separated list of actors
    @NotBlank(message="Plot is required")
    private String plot;
    @NotBlank(message="Language is required")
    private String language;
    @NotBlank(message="Country is required")
    private String country;
    @NotBlank(message="ImdbRating is required")
    private String imdbRating;

    private String owner;
    private String poster;

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
