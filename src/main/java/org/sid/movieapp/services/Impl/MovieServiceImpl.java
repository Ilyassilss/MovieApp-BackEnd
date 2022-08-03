package org.sid.movieapp.services.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.sid.movieapp.entities.Actor;
import org.sid.movieapp.entities.Category;
import org.sid.movieapp.entities.Director;
import org.sid.movieapp.entities.Image;
import org.sid.movieapp.entities.Movie;
import org.sid.movieapp.mappers.ActorMapper;
import org.sid.movieapp.mappers.CategoryMapper;
import org.sid.movieapp.mappers.DirectorMapper;
import org.sid.movieapp.mappers.ImageMapper;
import org.sid.movieapp.mappers.MovieMapper;
import org.sid.movieapp.models.requests.MovieRequest;
import org.sid.movieapp.models.responses.ImageResponse;
import org.sid.movieapp.models.responses.MovieResponse;
import org.sid.movieapp.models.responses.MovieResponseCover;
import org.sid.movieapp.repositories.ActorRepository;
import org.sid.movieapp.repositories.CategoryRepository;
import org.sid.movieapp.repositories.DirectorRepository;
import org.sid.movieapp.repositories.ImageRepository;
import org.sid.movieapp.repositories.MovieRepository;
import org.sid.movieapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired // l'injection des dependences
	MovieRepository movieRepository;

	@Autowired
	DirectorRepository directorRepository;

	@Autowired
	CategoryRepository categorieRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	MovieMapper mapper;

	@Autowired
	ActorMapper actorMapper;

	@Autowired
	CategoryMapper categorieMapper;

	@Autowired
	ImageMapper imageMapper;

	@Transactional
	@Override
	public MovieResponse add(MovieRequest movie) {

		Optional<Movie> findMovie = movieRepository.findByTitle(movie.getTitle());
		if (findMovie.isPresent())
			throw new RuntimeException("Movie exists");

		Optional<Director> findDirector = directorRepository.findByName(movie.getDirector().getName());
		Movie newMovie = Movie.builder().title(movie.getTitle()).description(movie.getDescription()).build();

		movieRepository.save(newMovie);
		newMovie.setActors(new HashSet<>());
		newMovie.setCategories(new HashSet<>());

		if (movie.getCategories() != null && !movie.getCategories().isEmpty()) {
			movie.getCategories().forEach(cat -> {
				Optional<Category> findCat = categorieRepository.findByName(cat.getName());
				if (categorieRepository.findByName(cat.getName()).isPresent()) {
					newMovie.getCategories().add(findCat.get());
				} else {
					Category categorie = Category.builder().description(cat.getDescription())
							.name(cat.getName()).build();
					newMovie.getCategories().add(categorie);
					//cat.setMovies(Collections.singletonList(newMovie));
					categorieRepository.save(categorie);
				}
			});
		}

		if (movie.getActors() != null && !movie.getActors().isEmpty()) {
			movie.getActors().forEach(act -> {
				Optional<Actor> findActor = actorRepository.findByFirstNameAndLastName(act.getFirstName(), act.getLastName());
				if (findActor.isPresent()) {
					newMovie.getActors().add(findActor.get());
				} else {
					Actor actor = Actor.builder().firstName(act.getFirstName()).lastName(act.getLastName())
							.age(act.getAge()).photoLink(act.getPhotoLink()).build();
					newMovie.getActors().add(actor);
					actorRepository.save(actor);
				}
			});
		}

		if (movie.getImages() != null && !movie.getImages().isEmpty()) {
			newMovie.setImages(ImageMapper.INSTANCE.mapImages(movie.getImages()));
			movie.getImages().forEach(img -> {
				Optional<Image> image = imageRepository.findByImageLink(img.getImageLink());
				if (!image.isPresent()) {
					image.get().setMovie(newMovie);
					imageRepository.save(image.get());
				}
			});
		}

		if (findDirector.isPresent()) {
			newMovie.setDirector(findDirector.get());
		} else {
			Director director = Director.builder().name(movie.getDirector().getName())
					.phone(movie.getDirector().getPhone()).build();
			directorRepository.save(director);
			newMovie.setDirector(director);
		}

		return mapper.entityToResponses(newMovie);
	}

	@Override
	public List<MovieResponse> getAll() {

		List<Movie> movies = movieRepository.findAll();
		List<MovieResponse> newMovies = new ArrayList<>();
		movies.forEach(movie -> {
			newMovies.add(mapper.entityToResponses(movie));
		});
		return newMovies;

	}
	
	@Override
	public Page<Movie> getAllPaginations(Pageable pageable) {

		return movieRepository.findAll(pageable);
	}

	@Override
	public Page<MovieResponseCover> getAllWithCoverPaginations(Pageable pageable) {
		List<MovieResponseCover> movieCovers= this.getAllWithCover();
		final int start = (int)pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), movieCovers.size());
		return new PageImpl<>(movieCovers.subList(start, end),pageable,movieCovers.size());
	}

	@Override
	public MovieResponse get(Long id) {
		return mapper.entityToResponses(movieRepository.findById(id).get());
	}

	@Override
	public void delete(Long id) {
		movieRepository.deleteById(id);
	}

	@Override
	public MovieResponse update(Long id, MovieRequest movie) {

		Movie movieup = movieRepository.findById(id).get();
		Optional<Director> findDirector = directorRepository.findByName(movie.getDirector().getName());
		movieup.setTitle(movie.getTitle());
		movieup.setDescription(movie.getDescription());

		if (findDirector.isPresent()) {
			movieup.setDirector(findDirector.get());
		}
		if (movie.getActors() == null)
			movieup.setActors(movieup.getActors());
		else
			movieup.setActors(ActorMapper.INSTANCE.mapActors(movie.getActors()));
		if (movie.getCategories() == null)
			movieup.setCategories(movieup.getCategories());
		else {
			movieup.getCategories().removeAll(movieup.getCategories());
			movie.getCategories().forEach(cat -> {
				Optional<Category> findCat = categorieRepository.findByName(cat.getName());
		 		if (findCat.isPresent()) {
					movieup.getCategories().add(findCat.get());
				} else {
					Category categorie = Category.builder().description(cat.getDescription())
							.name(cat.getName()).build();
					movieup.getCategories().add(categorie);
					categorieRepository.save(categorie);
				}
			});	
		}
		if (movie.getImages() == null)
			movieup.setImages(movieup.getImages());
		else
			movieup.setImages(ImageMapper.INSTANCE.mapImages(movie.getImages()));

		return mapper.entityToResponses(movieRepository.save(movieup));
	}

	@Override
	public List<MovieResponseCover> getAllWithCover() {
		List<MovieResponseCover> movieResponseCovers = new ArrayList<>();
		movieRepository.findAll().forEach(movie -> {
			MovieResponseCover movieResponseCover = MovieResponseCover.builder().description(movie.getDescription())
					.director(DirectorMapper.INSTANCE.entityToResponse(movie.getDirector())).id(movie.getId()).title(movie.getTitle())
					.timestamp(movie.getTimestamp()).build();

			Optional<Image> cover = movie.getImages().stream().filter(img -> img.getIsCover()).findFirst();
			if (cover.isPresent()) {
				ImageResponse coverResponse = ImageResponse.builder()
						.id(cover.get().getId())
						.imageLink(cover.get().getImageLink())
						.imageName(cover.get().getFileName())
						.imageType(cover.get().getImageType())
						.fileName(cover.get().getFileName())
						.build();
				movieResponseCover.setCover(coverResponse);
			}
			movieResponseCovers.add(movieResponseCover);
		});
		return movieResponseCovers;
	}
	
	@Override
	public Map<String, Object> getAllWithCoverPaginations(String title, Pageable pageable) {
		List<MovieResponseCover> movieResponseCovers = new ArrayList<>();
		Page<Movie> movies = (title.isBlank()) ? movieRepository.findAll(pageable)
				: movieRepository.findByTitleContainingOrderByTimestampDesc(title, pageable);
		movies.getContent().forEach(movie -> {
			MovieResponseCover movieResponseCover = MovieResponseCover.builder()
					.id(movie.getId())
					.title(movie.getTitle())
					.description(movie.getDescription())
					.director(DirectorMapper.INSTANCE.entityToResponse(movie.getDirector()))
					.timestamp(movie.getTimestamp())
					.build();

			Optional<Image> cover = movie.getImages()
					.stream()
					.filter(img -> img.getIsCover()).findFirst();
			if (cover.isPresent()) {
				ImageResponse coverResponse = ImageResponse.builder()
						.id(cover.get().getId())
						.imageLink(cover.get().getImageLink())
						.imageName(cover.get().getFileName())
						.imageType(cover.get().getImageType())
						.fileName(cover.get().getFileName())
						.build();
				movieResponseCover.setCover(coverResponse);
			}
			movieResponseCovers.add(movieResponseCover);
		});
		Map<String, Object> moviesResponse = new HashMap<>();
		moviesResponse.put("content", movieResponseCovers);
		moviesResponse.put("currentPage", movies.getNumber());
		moviesResponse.put("totalElements", movies.getTotalElements());
		moviesResponse.put("totalPages", movies.getTotalPages());
		return moviesResponse;
	}

}