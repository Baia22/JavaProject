package ge.bog.finalProject2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MOVIE")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "genres", nullable = false)
    private String genres;
    @Column(name = "view_count", nullable = false)
    private Long viewCount = (long)0;
    @Column(name = "recently_view_count", nullable = false)
    private Long recentlyViewCount = (long)0;
    @OneToMany(mappedBy = "movieId")
    private List<TagEntity> tags;
    @OneToMany(mappedBy = "movieId")
    private List<RatingEntity> ratings;

    public MovieEntity(Long id, String title, String genres) {
        this.id = id;
        this.title = title;
        this.genres = genres;
    }
    public MovieEntity(String title, String genres) {
        this.title = title;
        this.genres = genres;
    }
}
