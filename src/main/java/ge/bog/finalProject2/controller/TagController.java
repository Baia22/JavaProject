package ge.bog.finalProject2.controller;

import ge.bog.finalProject2.dto.Error;
import ge.bog.finalProject2.dto.Rating;
import ge.bog.finalProject2.dto.Tag;
import ge.bog.finalProject2.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping(value = "get-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTags(@RequestParam(required = false) String title, @RequestParam(required = false) Long movieId) {
        if (title == null && movieId == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide one of the params: title or movieId!"));
        }
        List<Tag> tags = tagService.getTagsByMovie(title, movieId);
        return ResponseEntity.ok(tags);
    }

    @PostMapping(value = "add-tag", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTag(@RequestBody Tag tag) {
        if(tag.getTag() == null || tag.getMovieId() == null || tag.getUserId() == null) {
            return ResponseEntity.badRequest().body(new Error("You should provide all of the params: tag, movieId and userId!"));
        }
        try {
            tagService.addTag(tag);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }
}
