package ge.bog.finalProject2.repository;

import ge.bog.finalProject2.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    MovieEntity getMovieByTitle(String title);
}
