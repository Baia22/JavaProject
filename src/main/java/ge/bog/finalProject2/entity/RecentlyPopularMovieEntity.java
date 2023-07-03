package ge.bog.finalProject2.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECENTLY_POPULAR_MOVIE")
public class RecentlyPopularMovieEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "movie_id", nullable = false)
    private Long movieId;
    @Column(name = "view_count", nullable = false)
    private Long viewCount;
    @Column(name = "insertion_date", nullable = false)
    private LocalDateTime insertionDate = LocalDateTime.now();;

    public RecentlyPopularMovieEntity(Long movieId, Long viewCount) {
        this.movieId = movieId;
        this.viewCount = viewCount;
    }
}
