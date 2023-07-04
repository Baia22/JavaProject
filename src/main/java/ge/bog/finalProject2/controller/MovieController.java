package ge.bog.finalProject2.controller;

import ge.bog.finalProject2.dto.Error;
import ge.bog.finalProject2.dto.Movie;
import ge.bog.finalProject2.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping(value = "get-movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovies(@RequestParam(required = false) String title,
                                       @RequestParam(required = false) String genre,
                                       @RequestParam(required = false) String tag) {
        if (title == null && genre == null && tag == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide one of the params: title, genre or tag!"));
        }
        List<Movie> movies = movieService.getMatchingMovies(title, genre, tag);
        if (movies.size() == 0) {
            return ResponseEntity.status(404).body(new Error("No movies found matching search criteria", Error.ErrorType.EmptyResult));
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "get-top-movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTopMovies() {
        List<Movie> movies = movieService.getTopMovies();
        if (movies.size() == 0) {
            return ResponseEntity.status(404).body(new Error("No movies found", Error.ErrorType.EmptyResult));
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "get-movie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovie(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) Long movieId) {
        if (title == null && movieId == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide title or movieId!"));
        }
        try {
            return ResponseEntity.ok(movieService.getMovie(title, movieId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping(value = "get-movies-pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMoviesPagination(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String tag,
            @RequestParam int first,
            @RequestParam int limit
    ) {
        if (title == null && genre == null && tag == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide one of the params: title, genre or tag!"));
        }
        List<Movie> movies = movieService.getMatchingMovies(title, genre, tag, first, limit);
        if (movies.size() == 0) {
            return ResponseEntity.status(404).body(new Error("No movies found matching search criteria", Error.ErrorType.EmptyResult));
        }
        return ResponseEntity.ok(movies);
    }

    @PostMapping(value = "add-movie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getGenres() == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide all of the params : title and genre!"));
        }
        try {
            return ResponseEntity.ok(movieService.addMovie(movie));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping(value = "get-recent-top-movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecentTopMovies() {
        List<Movie> movies = movieService.getRecentTopMovies();
        if (movies.size() == 0) {
            return ResponseEntity.status(404).body(new Error("No movies found", Error.ErrorType.EmptyResult));
        }
        return ResponseEntity.ok(movies);
    }
}