package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE :genre IS NULL OR m.genre = :genre ")
    Page<Movie> findAll(Genre genre, Pageable pageable);

    @Query("SELECT new com.devsuperior.movieflix.dto.ReviewDTO(r)" +
            "FROM Review r WHERE r.movie = :movie")
    List<ReviewDTO> findMovieReviews(Movie movie);
}
