package ge.bog.finalProject2.Scheduled;

import ge.bog.finalProject2.dao.MoviesDao;
import ge.bog.finalProject2.dao.RecentlyPopularMoviesDao;
import ge.bog.finalProject2.entity.MovieEntity;
import ge.bog.finalProject2.entity.RecentlyPopularMovieEntity;
import ge.bog.finalProject2.repository.MovieRepository;
import ge.bog.finalProject2.repository.RecentlyPopularMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Configuration
@EnableScheduling

public class SchedulerJob {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RecentlyPopularMovieRepository recentlyPopularMovieRepository;

    @Autowired
    MoviesDao movieDao;

    @Autowired
    RecentlyPopularMoviesDao recentlyPopularMoviesDao;


    @Scheduled(fixedDelay = 1000 * 60 * 3)
    public void updateRecentlyPopularMovies() {
        List<MovieEntity> movies = movieDao.getRecentlyViewedMovies();

        List<RecentlyPopularMovieEntity> recentlyPopularMovieEntities = movies.stream().map(movieEntity -> {
            RecentlyPopularMovieEntity recentlyPopularMovieEntity =
                    recentlyPopularMoviesDao.getRecentlyPopularMovie(movieEntity.getId());
            if (recentlyPopularMovieEntity == null) {
                return new RecentlyPopularMovieEntity(movieEntity.getId(), movieEntity.getRecentlyViewCount());
            } else {
                recentlyPopularMovieEntity.setViewCount(recentlyPopularMovieEntity.getViewCount()
                        + movieEntity.getRecentlyViewCount());
                return recentlyPopularMovieEntity;
            }
        }).collect(Collectors.toList());

        recentlyPopularMovieRepository.saveAll(recentlyPopularMovieEntities);

        for (MovieEntity movieEntity : movies) {
            movieEntity.setRecentlyViewCount((long)0);
        }
        movieRepository.saveAll(movies);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 20)
    public void deleteOverdueMovies() {
        recentlyPopularMoviesDao.deleteOverdueMovies();
    }
}
