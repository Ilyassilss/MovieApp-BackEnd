package org.sid.movieapp.repositories;

import org.sid.movieapp.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Page<Movie> findByTitleContainingOrderByTimestampDesc(String title, Pageable pageable);

    Optional<Movie> findByTitle(String title);
}
