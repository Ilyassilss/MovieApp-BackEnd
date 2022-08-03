package org.sid.movieapp.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.movieapp.entities.Movie;
import org.sid.movieapp.models.requests.MovieRequest;
import org.sid.movieapp.models.responses.MovieResponse;
import org.sid.movieapp.models.responses.MovieResponseCover;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Set<MovieResponseCover> mapMovie(Set<Movie> movies);

    Set<Movie> mapMovieResponse(Set<MovieRequest> movies);

    Movie requestToEntity(MovieRequest movieRequest);

    MovieResponseCover entityToResponse(Movie movie);

	MovieResponse entityToResponses(Movie movie);
}
