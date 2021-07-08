package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(Long genreId, Pageable pageable) {
        Genre genre = genreId > 0 ? genreRepository.getOne(genreId) : null;
        Page<Movie> result = repository.findAll(genre, pageable);
        return result.map(MovieDTO::new);
    }

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        Optional<Movie> movieOptional = repository.findById(id);
        return movieOptional.map(MovieDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findMovieReviews(Long id) {
        Movie movie = repository.getOne(id);
        return repository.findMovieReviews(movie);
    }
}
