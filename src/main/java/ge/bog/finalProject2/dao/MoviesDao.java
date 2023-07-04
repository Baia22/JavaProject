package ge.bog.finalProject2.dao;

import ge.bog.finalProject2.entity.MovieEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MoviesDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<MovieEntity> getMatchingMovie(String title, String genre, String tag, int first, int limit) {
        return entityManager.createQuery(
                "select m " +
                "from MovieEntity m " +
                "where m.title like :t " +
                "or m.genres like :g " +
                "or exists (select 1 from TagEntity t where t.movieId = m.id and t.tag = :tag)",
                MovieEntity.class)
                .setParameter("t", "%" + title + "%")
                .setParameter("g", "%" + genre + "%")
                .setParameter("tag", tag)
                .setFirstResult(first)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<MovieEntity> getTopMovies() {
        return entityManager.createQuery(
                "select m from MovieEntity m " +
                        "order by m.viewCount " +
                        "desc nulls last",
                         MovieEntity.class)
                .setMaxResults(5)
                .getResultList();
    }

    public List<MovieEntity>  getRecentlyViewedMovies() {
        return entityManager.createQuery(
                        "select m from MovieEntity m " +
                                "where m.recentlyViewCount>0 ",
                        MovieEntity.class)
                .getResultList();
    }

    public List<MovieEntity>  getRecentTopMovies() {
        return entityManager.createQuery(
                        "SELECT m " +
                                "FROM MovieEntity m " +
                                "INNER JOIN RecentlyPopularMovieEntity rm ON rm.movieId = m.id " +
                                "where EXTRACT(MINUTE FROM (CURRENT_TIMESTAMP - rm.insertionDate)) < 20 " +
                                "order by rm.viewCount desc nulls last"
                        ,
                        MovieEntity.class)
                .getResultList();
    }
}
