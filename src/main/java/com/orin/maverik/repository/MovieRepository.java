package com.orin.maverik.repository;

import com.orin.maverik.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findAllByImdbID(String imdbId);
    List<Movie> findAllByTitleContainingIgnoreCase(String title);
    List<Movie> findAllByActorsContainingIgnoreCase(String actor);
    List<Movie> findAllByGenre(String genre);
}
