package org.sid.movieapp.services;

import org.sid.movieapp.entities.Image;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.ImageRequest;
import org.sid.movieapp.models.responses.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {

    ImageResponse saveImage(Long movieID , MultipartFile file) throws IOException, NotFoundException;

    ImageResponse editImage(ImageRequest imageRequest) throws NotFoundException;

    ImageResponse getImage(String imageID) throws NotFoundException;

    ImageResponse saveImageLocal(Long movieID , MultipartFile file , Boolean isCover) throws IOException,NotFoundException;

    void init();

    Path getUploadPath();
}
