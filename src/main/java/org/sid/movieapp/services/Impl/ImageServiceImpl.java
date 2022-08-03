package org.sid.movieapp.services.Impl;

import org.sid.movieapp.entities.Image;
import org.sid.movieapp.entities.Movie;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.mappers.ImageMapper;
import org.sid.movieapp.models.requests.ImageRequest;
import org.sid.movieapp.models.responses.ImageResponse;
import org.sid.movieapp.repositories.ImageRepository;
import org.sid.movieapp.repositories.MovieRepository;
import org.sid.movieapp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class ImageServiceImpl implements ImageService {

    private final Path uploadPath = Paths.get("uploads");

    @Autowired
    private ImageRepository imageRepository ;

    @Autowired
    private MovieRepository movieRepository;



    @Override
    public ImageResponse saveImage(Long movieID, MultipartFile file) throws IOException , NotFoundException {
        Optional<Movie> movie = movieRepository.findById(movieID);
        if(movie.isPresent()){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = Image.builder()
                    .imageType(file.getContentType())
                    .fileName(fileName)
                    .file(file.getBytes())
                    .build();
            return ImageMapper.INSTANCE.entityToResponse(imageRepository.save(image));
        }
        throw new NotFoundException(Movie.class.getSimpleName());
    }

    @Override
    public ImageResponse editImage(ImageRequest imageRequest) throws NotFoundException{
        Optional<Image> image = imageRepository.findById(imageRequest.getId());
        if(!image.isPresent())
            throw new NotFoundException(Image.class.getSimpleName());
        return ImageMapper.INSTANCE.entityToResponse(image.get()) ;
    }

    @Override
    public ImageResponse getImage(String imageID) throws NotFoundException {
        return ImageMapper.INSTANCE.entityToResponse(
                imageRepository.findById(imageID).
                orElseThrow(()->new NotFoundException(Image.class.getSimpleName()))
        ) ;
    }

	@Transactional
    @Override
    public ImageResponse saveImageLocal(Long movieID, MultipartFile file, Boolean isCover) throws IOException,NotFoundException {
        Optional<Movie> movie = movieRepository.findById(movieID);
        if(movie.isPresent()){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Files.copy(file.getInputStream(),this.uploadPath.resolve(file.getOriginalFilename()));
            Image image = Image.builder()
                    .fileName(fileName)
                    .imageType(file.getContentType())
                    .isCover(isCover)
                    .build();
            image.setMovie(movie.get());
            if(isCover)
                imageRepository.updateAllCover(movieID);
            String imageUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/movie")
                    .path("/downloadFile/")
                    .path(image.getId())
                    .toUriString();
            image.setImageLink(imageUrl);
            return ImageMapper.INSTANCE.entityToResponse(imageRepository.save(image)) ;
        }
        throw new NotFoundException(Movie.class.getSimpleName());
    }

    @Override
    public void init() {
        boolean exists = Files.exists(uploadPath);
        if(!exists){
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create uploads folder");
            }

        }
    }

    @Override
    public Path getUploadPath() {
        return this.uploadPath;
    }
}
