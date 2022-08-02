package org.sid.movieapp.repositories;

import org.sid.movieapp.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director,Long> {
    Optional<Director> findByName(String name);
}
