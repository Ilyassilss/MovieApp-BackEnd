package org.sid.movieapp.services;


import java.util.List;
import java.util.Map;

import org.sid.movieapp.entities.Movie;
import org.sid.movieapp.models.requests.MovieRequest;
import org.sid.movieapp.models.responses.MovieResponse;
import org.sid.movieapp.models.responses.MovieResponseCover;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
	MovieResponse add(MovieRequest movie);

	List<MovieResponse> getAll();

	List<MovieResponseCover> getAllWithCover();
	
	Page<MovieResponseCover> getAllWithCoverPaginations(Pageable pageable);

	Map<String, Object> getAllWithCoverPaginations(String title,Pageable pageable);

	MovieResponse get(Long id);

	void delete(Long id);

	MovieResponse update(Long id, MovieRequest movie);

	Page<Movie> getAllPaginations(Pageable pageable);
}