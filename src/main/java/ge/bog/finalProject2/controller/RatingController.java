package ge.bog.finalProject2.controller;

import ge.bog.finalProject2.dto.Error;
import ge.bog.finalProject2.dto.Rating;
import ge.bog.finalProject2.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping(value = "get-ratings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRatings(@RequestParam(required = false) String title,
                                        @RequestParam(required = false) Long movieId) {
        if (title == null && movieId == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide one of the params: title or movieId!"));
        }
        List<Rating> rating = ratingService.getRatingsByMovie(title, movieId);
        return ResponseEntity.ok(rating);
    }

    @PostMapping(value = "add-rating", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRating(@RequestBody Rating rating) {
        if(rating.getRating() == null || rating.getMovieId() == null || rating.getUserId() == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide all of the params: rating, movieId and userId!"));
        }
        try {
            ratingService.addRating(rating);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }
}
