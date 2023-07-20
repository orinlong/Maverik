package com.orin.maverik.controller;


import com.orin.maverik.domain.Movie;
import com.orin.maverik.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value="/movies")
public class MovieController {


    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /*
     * Calling this endpoint will load the database with entries from the Maverik movie endpoint
     */
    @GetMapping(value="/loaddatabase")
    public ResponseEntity<Object> loadDatabase() {
        movieService.loadDatabase();

        return new ResponseEntity<>(null, HttpStatus.OK);
    }



    @GetMapping(value="/title/{title}")
    public ResponseEntity<Object> getByTitle(@PathVariable String title) {
        return new ResponseEntity<>(movieService.getByTitle(title), HttpStatus.OK);
    }


    @GetMapping(value="/genre/{genre}")
    public ResponseEntity<Object> getByGenre(@PathVariable String genre) {
        return new ResponseEntity<>(movieService.getByGenre(genre), HttpStatus.OK);
    }

    @GetMapping(value="/imdbid/{imdbid}")
    public ResponseEntity<Object> getByImdbId(@PathVariable String imdbid) {
        return new ResponseEntity<>(movieService.getByImdbID(imdbid), HttpStatus.OK);
    }

    @GetMapping(value="/actors/{actors}")
    public ResponseEntity<Object> getByActors(@PathVariable String actors) {
        return new ResponseEntity<>(movieService.getByActors(actors), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<Object> saveNewMovie(@Valid @RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.saveNewMovie(movie), HttpStatus.CREATED);
    }
    @GetMapping(value="/{id}")
    public ResponseEntity<Object> getMovie(@PathVariable String id) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        return movieOptional.<ResponseEntity<Object>>map(movie -> new ResponseEntity<>(movie, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable String id, @Valid @RequestBody Movie movie) {
        Movie updated = movieService.updateMovie(id, movie);
        if (updated == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
