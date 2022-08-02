package org.sid.movieapp.repositories;

import org.sid.movieapp.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
    @Modifying
    @Query("UPDATE Image i SET i.isCover=0 WHERE i.movie.id= :movie_id")
    Integer updateAllCover(@Param("movie_id") Long movieId);
}