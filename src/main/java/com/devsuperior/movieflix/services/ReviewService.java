package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public ReviewDTO save(ReviewDTO dto) {
        Review review = mapToEntity(dto);
        review = repository.save(review);
        return new ReviewDTO(review);
    }

    private Review mapToEntity(ReviewDTO dto) {
        User user = authService.authenticated();
        Movie movie = movieRepository.getOne(dto.getMovieId());

        Review entity = new Review();
        entity.setMovie(movie);
        entity.setText(dto.getText());
        entity.setUser(user);

        return entity;
    }
}
