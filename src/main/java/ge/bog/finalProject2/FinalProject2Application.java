package ge.bog.finalProject2;

import com.opencsv.CSVReader;
import ge.bog.finalProject2.entity.MovieEntity;
import ge.bog.finalProject2.entity.RatingEntity;
import ge.bog.finalProject2.entity.TagEntity;
import ge.bog.finalProject2.repository.MovieRepository;
import ge.bog.finalProject2.repository.RatingRepository;
import ge.bog.finalProject2.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FinalProject2Application {
	private final MovieRepository movieRepository;
	private final TagRepository tagRepository;
	private final RatingRepository ratingRepository;

	public static void main(String[] args) {
		SpringApplication.run(FinalProject2Application.class, args);
	}

	@Autowired
	public FinalProject2Application(MovieRepository movieRepository, TagRepository tagRepository, RatingRepository ratingRepository) {
		this.movieRepository = movieRepository;
		this.tagRepository = tagRepository;
		this.ratingRepository = ratingRepository;
	}

	@PostConstruct
	public void saveAllFromCsv() {
		saveTagsFromCsv();
		saveMoviesFromCsv();
		saveRatingsFromCsv();
	}

	private void saveMoviesFromCsv() {
		try {
			List<MovieEntity> movies = new ArrayList<>();
			CSVReader csvReader = getCsvReaderWithoutHeaderFor("movies.csv");
			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				Long id = Long.parseLong(nextRecord[0]);
				String title = nextRecord[1];
				String genres = nextRecord[2];
				MovieEntity movie = new MovieEntity(id, title, genres);
				movies.add(movie);
			}
			movieRepository.saveAll(movies);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveRatingsFromCsv() {
		try {
			List<RatingEntity> ratings = new ArrayList<>();
			CSVReader csvReader = getCsvReaderWithoutHeaderFor("ratings.csv");
			String[] nextRecord;
			long counter = 1;
			while ((nextRecord = csvReader.readNext()) != null) {
				Long userId = Long.parseLong(nextRecord[0]);
				Long movieId = Long.parseLong(nextRecord[1]);
				Double rating = Double.parseDouble(nextRecord[2]);
				Long timestamp = Long.parseLong(nextRecord[3]);
				RatingEntity ratingEntity = new RatingEntity(counter++, userId, movieId, rating, timestamp);
				ratings.add(ratingEntity);
			}
			ratingRepository.saveAll(ratings);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTagsFromCsv() {
		try {
			List<TagEntity> tags = new ArrayList<>();
			CSVReader csvReader = getCsvReaderWithoutHeaderFor("tags.csv");
			String[] nextRecord;
			long counter = 1;
			while ((nextRecord = csvReader.readNext()) != null) {
				Long userId = Long.parseLong(nextRecord[0]);
				Long movieId = Long.parseLong(nextRecord[1]);
				String tag = nextRecord[2];
				Long timestamp = Long.parseLong(nextRecord[3]);
				TagEntity tagEntity = new TagEntity(counter++, userId, movieId, tag, timestamp);
				tags.add(tagEntity);
			}
			tagRepository.saveAll(tags);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CSVReader getCsvReaderWithoutHeaderFor(String path) throws IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(path);
		Reader targetReader = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(targetReader);
		csvReader.readNext();
		return csvReader;
	}
}
