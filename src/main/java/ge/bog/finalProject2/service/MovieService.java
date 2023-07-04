package ge.bog.finalProject2.service;

import ge.bog.finalProject2.dao.MoviesDao;
import ge.bog.finalProject2.dto.Movie;
import ge.bog.finalProject2.entity.MovieEntity;
import ge.bog.finalProject2.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MoviesDao moviesDao;
    public List<Movie> getMatchingMovies(String title, String genre, String tag) {
        return getMatchingMovies(title, genre, tag, 0, 10);
    }

    public List<Movie> getMatchingMovies(String title, String genre, String tag, int first, int limit) {
        return moviesDao
                .getMatchingMovie(title, genre, tag, first, limit)
                .stream()
                .map(movieEntity -> new Movie(
                        movieEntity.getId(),
                        movieEntity.getTitle(),
                        movieEntity.getGenres(),
                        movieEntity.getViewCount()
                ))
                .collect(Collectors.toList());
    }

    public long addMovie(Movie movie) throws InstanceAlreadyExistsException {
        if (movieRepository.getMovieByTitle(movie.getTitle()) != null) {
            throw new InstanceAlreadyExistsException("This movie has already exists!");
        }
        MovieEntity movieEntity = new MovieEntity(movie.getTitle(), movie.getGenres());
        movieEntity = movieRepository.save(movieEntity);
        return movieEntity.getId();
    }

    public Movie getMovie(String title, Long movieId) throws InstanceNotFoundException {
        MovieEntity movieEntity;
        if (movieId != null) {
            movieEntity = movieRepository.getReferenceById(movieId);
        } else {
            movieEntity = movieRepository.getMovieByTitle(title);
        }
        if (movieEntity == null) {
            throw new InstanceNotFoundException("This movie does not exists!");
        }
        movieEntity.setViewCount(movieEntity.getViewCount() == null ? 0 : movieEntity.getViewCount() + 1);
        movieEntity.setRecentlyViewCount(movieEntity.getRecentlyViewCount() == null ? 0 : movieEntity.getRecentlyViewCount() + 1);
        movieRepository.save(movieEntity);
        return new Movie(movieEntity.getId(), movieEntity.getTitle(), movieEntity.getGenres(), movieEntity.getViewCount());
    }

    public List<Movie> getTopMovies() {
        return moviesDao
                .getTopMovies()
                .stream()
                .map(movieEntity -> new Movie(
                        movieEntity.getId(),
                        movieEntity.getTitle(),
                        movieEntity.getGenres(),
                        movieEntity.getViewCount()
                ))
                .collect(Collectors.toList());
    }

    public List<Movie> getRecentTopMovies() {
        return moviesDao
                .getRecentTopMovies()
                .stream()
                .map(movieEntity -> new Movie(
                        movieEntity.getId(),
                        movieEntity.getTitle(),
                        movieEntity.getGenres(),
                        movieEntity.getViewCount()
                ))
                .collect(Collectors.toList());
    }
}
