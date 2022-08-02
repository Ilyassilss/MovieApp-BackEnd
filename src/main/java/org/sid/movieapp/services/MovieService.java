package org.sid.movieapp.services;

import org.sid.movieapp.entities.Movie;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.MovieRequest;
import org.sid.movieapp.models.responses.MovieResponseCover;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

public interface MovieService {
    MovieResponseCover add(MovieRequest movieRequest) throws AlreadyExistsException;

    Set<MovieResponseCover> get();

    MovieResponseCover get(Long id) throws NotFoundException;

    MovieResponseCover update(Long id , MovieRequest movieRequest)throws  NotFoundException;

    void delete(Long id);

    Set<MovieResponseCover> getAllWithCover();

    Page<Movie> getAllPaginations(Pageable pageable);

    Map<String,Object> getAllWithCoverPaginations(String title , Pageable pageable);
}
