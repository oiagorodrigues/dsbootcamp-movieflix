package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ReviewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Long movieId;

    @NotBlank(message = "Campo texto não pode ser vazio")
    private String text;

    private UserDTO user;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, Long movieId, String text, UserDTO user) {
        this.id = id;
        this.movieId = movieId;
        this.text = text;
        this.user = user;
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        movieId = entity.getMovie().getId();
        text = entity.getText();
        this.user = new UserDTO(entity.getUser());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewDTO reviewDTO = (ReviewDTO) o;

        return getId().equals(reviewDTO.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }
}
