package com.orin.maverik.services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.orin.maverik.config.RestService;
import com.orin.maverik.config.SetupConfiguration;
import com.orin.maverik.domain.Movie;
import com.orin.maverik.domain.MovieSearchResponse;
import com.orin.maverik.repository.MovieRepository;
import com.orin.maverik.util.MapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final RestService restService;


    public MovieService(MovieRepository movieRepository, SetupConfiguration setupConfiguration) {
        this.movieRepository = movieRepository;
        this.restService = setupConfiguration.createRestService();
    }


    public void loadDatabase() {
        // We will do a search of movies with these words in the title to add them to our db
        List<String> titles = List.of("Lion", "Flower", "Eye", "Princess", "Dinosaur", "Jurassic");
        Set<Movie> movieSet = new HashSet<>();

        titles.forEach(oneTitle -> {
            // Search for movies with the keyword in the title
            ObjectNode jsonResponse = restService.sendRestRequest("https://gateway.maverik.com/movie/api/movie/title/" + oneTitle + "?source=web", null, HttpMethod.GET);
            if (String.valueOf(jsonResponse.get("httpResponseStatus")).startsWith("2")) {
                if (jsonResponse.has("body") && jsonResponse.get("body").isArray()) {
                    ArrayNode listOfTitles = (ArrayNode) jsonResponse.get("body");
                    for (int i = 0; i < listOfTitles.size(); i++) {
                        // Get the details of each movie and add that movie to our Set (set to prevent duplicates based on IMDB id)
                        ObjectNode oneMovieResponse = restService.sendRestRequest("https://gateway.maverik.com/movie/api/movie/" + listOfTitles.get(i).get("imdbID").asText() + "?source=web", null, HttpMethod.GET);
                        movieSet.add(MapperUtil.convertToObject(oneMovieResponse, Movie.class));
                    }
                }
            }
        });

        if (!CollectionUtils.isEmpty(movieSet)) {
            movieRepository.saveAll(movieSet);
        }
    }


    public Movie saveNewMovie(Movie movie) {
        // Make sure the movie doesn't already exist by imdbId
        List<Movie> existing = movieRepository.findAllByImdbID(movie.getImdbID());
        if (CollectionUtils.isEmpty(existing)) {
            return movieRepository.save(movie);
        }

        return existing.get(0);
    }

    public Optional<Movie> getMovieById(String id) {
        if (StringUtils.isNumeric(id.trim())) {
            return movieRepository.findById(Integer.parseInt(id.trim()));
        }
        return Optional.empty();
    }

    public Movie updateMovie(String id, Movie movie) {
        if (StringUtils.isNumeric(id.trim())) {
            movie.setId(Integer.parseInt(id.trim()));
           return movieRepository.save(movie);
        }
        return null;
    }


    public void deleteMovie(String id) {
        if (StringUtils.isNumeric(id.trim())) {
            movieRepository.deleteById(Integer.parseInt(id.trim()));
        }
    }


    public MovieSearchResponse getByTitle(String title) {
        return new MovieSearchResponse(movieRepository.findAllByTitleContainingIgnoreCase(title));
    }


    public MovieSearchResponse getByGenre(String genre) {
        return new MovieSearchResponse(movieRepository.findAllByGenre(genre));
    }


    public MovieSearchResponse getByImdbID(String imdbId) {
        return new MovieSearchResponse(movieRepository.findAllByImdbID(imdbId));
    }


    public MovieSearchResponse getByActors(String actors) {
        return new MovieSearchResponse(movieRepository.findAllByActorsContainingIgnoreCase(actors));
    }

}
