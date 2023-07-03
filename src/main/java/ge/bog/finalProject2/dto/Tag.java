package ge.bog.finalProject2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

 @AllArgsConstructor
 @Getter
 @Setter
 public class Tag {
     private Long userId;
     private Long movieId;
     private String tag;
     private Long timestamp;
 }

