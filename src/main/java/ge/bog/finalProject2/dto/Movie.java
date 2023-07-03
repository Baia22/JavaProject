package ge.bog.finalProject2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private String genres;
    private Long viewCount;
}
