package com.example.demo.controllers;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

    private final MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Movie> listMovies() {
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Movie> listMovieById(@PathVariable("id") long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return repository.save(movie);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Movie> updateMovieById(@PathVariable("id") long id,
                                                 @RequestBody Movie movie) {
        return repository.findById(id)
                .map(record -> {
                    record.setTitle(movie.getTitle());
                    record.setCastr(movie.getCastr());
                    record.setDescr(movie.getDescr());
                    record.setUrlCover(movie.getUrlCover());
                    Movie updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deleteMovieById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}






