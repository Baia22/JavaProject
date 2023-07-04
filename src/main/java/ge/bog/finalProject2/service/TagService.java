package ge.bog.finalProject2.service;

import ge.bog.finalProject2.dto.Tag;
import ge.bog.finalProject2.entity.TagEntity;
import ge.bog.finalProject2.repository.MovieRepository;
import ge.bog.finalProject2.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TagRepository tagRepository;
    public List<Tag> getTagsByMovie(String title, Long movieId) {
        List<TagEntity> entities;
        if (title != null) {
            entities = movieRepository
                    .getMovieByTitle(title)
                    .getTags();
        }
        else {
            entities = movieRepository
                    .getReferenceById(movieId)
                    .getTags();
        }
        return entities
                .stream()
                .map(e -> new Tag(e.getUserId(), e.getMovieId(), e.getTag(), e.getTimestamp()))
                .collect(Collectors.toList());
    }

    public void addTag(Tag tag) throws InstanceAlreadyExistsException {
        if (movieRepository.getReferenceById(tag.getMovieId()).getTags().stream()
                .anyMatch(tagEntity -> tagEntity.getTag().equals(tag.getTag()))) {
            throw new InstanceAlreadyExistsException("This movie already has that tag!");
        }
        TagEntity tagEntity = new TagEntity(tag.getUserId(), tag.getMovieId(), tag.getTag(), 0);
        tagRepository.save(tagEntity);
    }
}
