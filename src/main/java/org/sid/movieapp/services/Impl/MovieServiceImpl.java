package org.sid.movieapp.services.Impl;

import org.sid.movieapp.entities.*;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.mappers.ActorMapper;
import org.sid.movieapp.mappers.CategoryMapper;
import org.sid.movieapp.mappers.DirectorMapper;
import org.sid.movieapp.mappers.MovieMapper;
import org.sid.movieapp.models.requests.DirectorRequest;
import org.sid.movieapp.models.requests.MovieRequest;
import org.sid.movieapp.models.responses.ImageResponse;
import org.sid.movieapp.models.responses.MovieResponseCover;
import org.sid.movieapp.repositories.*;
import org.sid.movieapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository ;

    @Autowired
    private DirectorRepository directorRepository ;

    @Autowired
    private ActorRepository actorRepository ;

    @Autowired
    private CategoryRepository categoryRepository ;

    @Autowired
    private ImageRepository imageRepository ;

    @Transactional
    @Override
    public MovieResponseCover add(MovieRequest movieRequest) throws AlreadyExistsException{
        Optional<Movie> findmovie = movieRepository.findByTitle(movieRequest.getTitle());
        if(findmovie.isPresent()){
            throw new AlreadyExistsException(movieRequest.getTitle(),Movie.class.getSimpleName());
        }else{

            Movie movie = new Movie();

            movie.setActors(new HashSet<>());
            movie.setCategories(new HashSet<>());
            movie.setImages(new HashSet<>());

            movie.setTitle(movieRequest.getTitle());
            movie.setDescription(movieRequest.getDescription());
            movieRepository.save(movie);
            Optional<Director> findDirector = directorRepository.findByName(movieRequest.getDirector().getName());
            // DIRECTOR //
            if(!findDirector.isPresent()){
                Director director = Director.builder()
                        .name(movieRequest.getDirector().getName())
                        .phone(movieRequest.getDirector().getPhone())
                        .build();
                directorRepository.save(director);

                movie.setDirector(director);
            } else movie.setDirector(findDirector.get());

            // ACTOR //
            if(movieRequest.getActors() != null && !movieRequest.getActors().isEmpty()){
                movieRequest.getActors().forEach(actor -> {
                    Optional<Actor> findActor = actorRepository.findByFirstNameAndLastName(actor.getFirstName(),actor.getLastName());
                    if(!findActor.isPresent()){
                        Actor act = Actor.builder()
                                .firstName(actor.getFirstName())
                                .lastName(actor.getLastName())
                                .age(actor.getAge())
                                .photoLink(actor.getPhotoLink())
                                .build();
                        actorRepository.save(act);
                        movie.getActors().add(act);
                    }else{
                        movie.getActors().add(findActor.get());
                    }

                });
            }

            // CATEGORY //
            if(movieRequest.getCategories() != null && !movieRequest.getCategories().isEmpty()){
                movieRequest.getCategories().forEach(category -> {
                    Optional<Category> findCategory = categoryRepository.findByName(category.getName());
                    if(!findCategory.isPresent()){
                        Category cat = Category.builder()
                                .name(category.getName())
                                .description(category.getDescription())
                                .build();
                        categoryRepository.save(cat);
                        movie.getCategories().add(cat);
                    }else{
                        movie.getCategories().add(findCategory.get());
                    }

                });
            }

            // IMAGE //
            if(movieRequest.getImages() != null && movieRequest.getImages().isEmpty()){
                movieRequest.getImages().forEach(image -> {
                    Optional<Image> findImage = imageRepository.findById(image.getId());
                    if(!findImage.isPresent()){
                        Image img = Image.builder()
                                .fileName(image.getFileName())
                                .file(image.getFile())
                                .isCover(image.getIsCover())
                                .imageType(image.getImageType())
                                .build();
                        imageRepository.save(img);
                        movie.getImages().add(img);
                    }else{
                        movie.getImages().add(findImage.get());
                    }
                });
            }
            return MovieMapper.INSTANCE.entityToResponse(movieRepository.save(movie));
        }

    }

    @Override
    public Set<MovieResponseCover> get() {
        return MovieMapper.INSTANCE.mapMovie(movieRepository.findAll().stream().collect(Collectors.toSet())) ;
    }

    @Override
    public MovieResponseCover get(Long id) throws NotFoundException{
        Optional<Movie> findMovie = movieRepository.findById(id);
        if(!findMovie.isPresent())
            throw new NotFoundException(Movie.class.getSimpleName());
        return MovieMapper.INSTANCE.entityToResponse(findMovie.get());
    }

    @Override
    public MovieResponseCover update(Long id, MovieRequest movieRequest) throws NotFoundException {
        Optional<Movie> findMovie = movieRepository.findById(id);
        if(findMovie.isPresent()){
            findMovie.get().setDescription(movieRequest.getDescription());
            findMovie.get().setTitle(movieRequest.getTitle());

            Optional<Director> findDirector = directorRepository.findByName(movieRequest.getDirector().getName());
            if(findDirector.isPresent()) findMovie.get()
                    .setDirector(findDirector.get());


            if(movieRequest.getActors() != null && !movieRequest.getActors().isEmpty()){
                movieRequest.getActors().forEach(actor -> {
                    if(!actorRepository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName()).isPresent()){
                        actorRepository.save(ActorMapper.INSTANCE.requestToEntity(actor));
                    }
                    findMovie.get().getActors().add(ActorMapper.INSTANCE.requestToEntity(actor));
                });
            }else {
                // keeping the same actors
                findMovie.get().setActors(findMovie.get().getActors());
            }

            if(movieRequest.getCategories() != null && !movieRequest.getCategories().isEmpty()){
                movieRequest.getCategories().forEach( category -> {
                    if(!categoryRepository.findByName(category.getName()).isPresent()){
                        categoryRepository.save(CategoryMapper.INSTANCE.requestToEntity(category));
                    }
                    findMovie.get().getCategories().add(CategoryMapper.INSTANCE.requestToEntity(category));
                });
            }else {
                findMovie.get().setCategories(findMovie.get().getCategories());
            }



            return MovieMapper.INSTANCE.entityToResponse(movieRepository.save(findMovie.get())) ;
        }
        throw new NotFoundException(Movie.class.getSimpleName());
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Set<MovieResponseCover> getAllWithCover() {
        List<MovieResponseCover> movieResponseCovers = new ArrayList<>();
        movieRepository.findAll().forEach(movie -> {
            MovieResponseCover movieResponseCover = MovieResponseCover.builder()
                    .id(movie.getId())
                    .title(movie.getDescription())
                    .description(movie.getDescription())
                    .director(DirectorMapper.INSTANCE.entityToResponse(movie.getDirector()))
                    .timestamp(movie.getTimestamp())
                    .build();
            Optional<Image> cover = movie.getImages().stream()
                    .filter(Image::getIsCover).findFirst();
            if(cover.isPresent()){
                ImageResponse coverResponse = ImageResponse.builder()
                        .id(cover.get().getId())
                        .imageLink(cover.get().getImageLink())
                        .imageName(cover.get().getFileName())
                        .imageType(cover.get().getImageType())
                        .build();
                movieResponseCover.setCover(coverResponse);
                movieResponseCovers.add(movieResponseCover);
            }
        });

        return movieResponseCovers.stream().collect(Collectors.toSet());
    }

    @Override
    public Page<Movie> getAllPaginations(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Map<String, Object> getAllWithCoverPaginations(String title, Pageable pageable) {
        List<MovieResponseCover> movieResponseCovers = new ArrayList<>();
        Page<Movie> movies = (title==null) ? movieRepository.findAll(pageable)
                : movieRepository.findByTitleContainingOrderByTimestampDesc(title,pageable);
        movies.getContent().forEach(
                movie -> {
                    MovieResponseCover movieResponseCover = MovieResponseCover.builder()
                            .id(movie.getId())
                            .description(movie.getDescription())
                            .title(movie.getTitle())
                            .director(DirectorMapper.INSTANCE.entityToResponse(movie.getDirector()))
                            .timestamp(movie.getTimestamp())
                            .build();

                    Optional<Image> cover = movie.getImages().stream()
                            .filter(img -> img.getIsCover())
                            .findFirst();
                    if(cover.isPresent()){
                        ImageResponse coverResponse = ImageResponse.builder()
                                .id(cover.get().getId())
                                .imageLink(cover.get().getImageLink())
                                .imageName(cover.get().getFileName())
                                .imageType(cover.get().getImageType())
                                .build();
                    }
                }
        );
        Map<String,Object> moviesResponse = new HashMap<>();
        moviesResponse.put("content",movieResponseCovers);
        moviesResponse.put("currentPage",movies.getNumber());
        moviesResponse.put("totalElements",movies.getTotalElements());
        moviesResponse.put("totalPages",movies.getTotalPages());
        return moviesResponse;
    }
}
