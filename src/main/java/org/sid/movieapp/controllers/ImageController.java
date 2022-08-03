package org.sid.movieapp.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.mappers.ImageMapper;
import org.sid.movieapp.models.responses.ImageResponse;
import org.sid.movieapp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/api/movie")
public class ImageController {
    @Autowired
    private ImageService imageService ;

    @PostMapping("/upload/{movieId}")
    public ResponseEntity<ImageResponse> add(@PathVariable("movieId") Long movieId ,
                                             @RequestParam("file")MultipartFile file) throws NotFoundException, IOException {
        ImageResponse saveImage = imageService.saveImage(movieId,file);
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/movie")
                .path("/download/")
                .path(saveImage.getId())
                .toUriString();
		saveImage.setImageLink(imageUrl);
        imageService.editImage(ImageMapper.INSTANCE.responseToRequest(saveImage)) ;
        ImageResponse response = ImageResponse.builder()
                .id(saveImage.getId())
                .imageLink(saveImage.getImageLink())
                .imageName(saveImage.getImageName())
                .imageSize(saveImage.getImageSize())
                .imageType(saveImage.getImageType())
                .build();
        return new ResponseEntity<ImageResponse>(response, HttpStatus.CREATED);
    }

    @PostMapping("/uploadFile/{movieId}")
    public ResponseEntity<ImageResponse> addFile(@PathVariable("movieId") Long movieId,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("is_cover") Boolean isCover) throws NotFoundException, IOException {
        ImageResponse saveImage = imageService.saveImageLocal(movieId,file,isCover);

        ImageResponse response = ImageResponse.builder()
                .id(saveImage.getId())
                .imageType(saveImage.getImageType())
                .imageSize(saveImage.getImageSize())
                .imageName(saveImage.getImageLink())
                .imageLink(saveImage.getImageLink())
                .build();

        return new ResponseEntity<ImageResponse>(response,HttpStatus.CREATED);
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> get(@PathVariable("imageId") String imageId) throws NotFoundException {
        ImageResponse image =imageService.getImage(imageId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ image.getFileName())
                .body(new ByteArrayResource(image.getFile()));

    }

    @GetMapping("/downloadFile/{imageId}")
    public ResponseEntity<Resource> getFile(@PathVariable("imageId") String imageId) throws NotFoundException, MalformedURLException {
        ImageResponse image = imageService.getImage(imageId);
        Path file = imageService.getUploadPath().resolve(image.getFileName());
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFileName())
                .body(resource);

    }
}
