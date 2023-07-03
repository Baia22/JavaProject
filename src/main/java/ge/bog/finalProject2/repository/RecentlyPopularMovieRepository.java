package ge.bog.finalProject2.repository;

import ge.bog.finalProject2.entity.RecentlyPopularMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentlyPopularMovieRepository extends JpaRepository<RecentlyPopularMovieEntity, Long>{
}
