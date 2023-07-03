package ge.bog.finalProject2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TAG")
public class TagEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(name = "movieId", nullable = false)
    private Long movieId;
    @Column(name = "tag", nullable = false)
    private String tag;
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    public TagEntity(Long userId, Long movieId, String tag, long timestamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.tag = tag;
        this.timestamp = timestamp;
    }
}
