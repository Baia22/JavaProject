package ge.bog.finalProject2.service;

import ge.bog.finalProject2.dto.Rating;
import ge.bog.finalProject2.entity.RatingEntity;
import ge.bog.finalProject2.repository.MovieRepository;
import ge.bog.finalProject2.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RatingRepository ratingRepository;
    public List<Rating> getRatingsByMovie(String title, Long movieId) {
        List<RatingEntity> entities;
        if (title != null) {
            entities = movieRepository
                    .getMovieByTitle(title)
                    .getRatings();
        }
        else {
           entities = movieRepository
                   .getReferenceById(movieId)
                   .getRatings();
        }
        return entities
                .stream()
                .map(e -> new Rating(e.getUserId(), e.getMovieId(), e.getRating(), e.getTimestamp()))
                .collect(Collectors.toList());
    }

    public void addRating(Rating rating) throws InstanceAlreadyExistsException {
        if (movieRepository.getReferenceById(rating.getMovieId()).getRatings().stream()
                .anyMatch(ratingEntity -> ratingEntity.getUserId().equals(rating.getUserId())) ) {
            throw new InstanceAlreadyExistsException("This user has already rated this movie!");
        }
        RatingEntity ratingEntity = new RatingEntity(rating.getUserId(), rating.getMovieId(), rating.getRating(), (long)0);
        ratingRepository.save(ratingEntity);
    }
}
