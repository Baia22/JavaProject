package ge.bog.finalProject2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Rating {
    private Long userId;
    private Long movieId;
    private Double rating;
    private Long timestamp;
}
