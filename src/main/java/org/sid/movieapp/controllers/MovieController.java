package org.sid.movieapp.controllers;

import org.sid.movieapp.entities.Movie;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.MovieRequest;
import org.sid.movieapp.models.responses.MovieResponseCover;
import org.sid.movieapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieResponseCover> add(@RequestBody @Valid MovieRequest movieRequest) throws AlreadyExistsException {
        MovieResponseCover movie = movieService.add(movieRequest);
        return new ResponseEntity<MovieResponseCover>(movie, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getPaginations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "", name = "title") String title) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<Map<String, Object>>
                (movieService.getAllWithCoverPaginations(title, pageable), HttpStatus.OK);
    }

    @GetMapping("allOld")
    public ResponseEntity<Page<Movie>> getPaginationsOld(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<Page<Movie>>
                (movieService.getAllPaginations(pageable),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<MovieResponseCover>> get(){
        return new ResponseEntity<Set<MovieResponseCover>>
                (movieService.get(),HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<Set<MovieResponseCover>> getWithCover(){
        return new ResponseEntity<Set<MovieResponseCover>>
                (movieService.getAllWithCover(),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        movieService.delete(id);
        return new ResponseEntity<String>("Deleted ! " , HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseCover> update(@PathVariable Long id ,@RequestBody MovieRequest movie) throws NotFoundException {
        return new ResponseEntity<MovieResponseCover>
                (movieService.update(id,movie),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseCover> get(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<MovieResponseCover>
                (movieService.get(id),HttpStatus.OK);
    }
}