package org.sid.movieapp.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.sid.movieapp.entities.Movie;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.MovieRequest;
import org.sid.movieapp.models.responses.MovieResponse;
import org.sid.movieapp.models.responses.MovieResponseCover;
import org.sid.movieapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieResponse> add(@RequestBody @Valid MovieRequest movieRequest) throws AlreadyExistsException {
        MovieResponse movie = movieService.add(movieRequest);
        return new ResponseEntity<MovieResponse>(movie, HttpStatus.CREATED);
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

    @GetMapping("/allOld")
    public ResponseEntity<Page<Movie>> getPaginationsOld(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<Page<Movie>>
                (movieService.getAllPaginations(pageable),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> get(){
        return new ResponseEntity<List<MovieResponse>>
                (movieService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<List<MovieResponseCover>> getWithCover(){
        return new ResponseEntity<List<MovieResponseCover>>
                (movieService.getAllWithCover(),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        movieService.delete(id);
        return new ResponseEntity<String>("Deleted ! " , HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id ,@RequestBody MovieRequest movie) throws NotFoundException {
        return new ResponseEntity<MovieResponse>
                (movieService.update(id,movie),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> get(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<MovieResponse>
                (movieService.get(id),HttpStatus.OK);
    }
}