package ge.bog.finalProject2.dao;

import ge.bog.finalProject2.entity.MovieEntity;
import ge.bog.finalProject2.entity.RecentlyPopularMovieEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecentlyPopularMoviesDao {
    @PersistenceContext
    private EntityManager entityManager;
    public RecentlyPopularMovieEntity getRecentlyPopularMovie(Long movieId) {
        try {
            return entityManager.createQuery(
                            "select m from RecentlyPopularMovieEntity m " +
                                    "where m.movieId = :mi  ",
                            RecentlyPopularMovieEntity.class).
                    setParameter("mi", movieId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Transactional
    public void deleteOverdueMovies() {
        entityManager.createQuery(
                "DELETE FROM RecentlyPopularMovieEntity m " +
                        "WHERE EXTRACT(MINUTE FROM (CURRENT_TIMESTAMP - m.insertionDate)) > 20")
                .executeUpdate();
    }
}
