package org.sid.movieapp.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.movieapp.entities.Image;
import org.sid.movieapp.models.requests.ImageRequest;
import org.sid.movieapp.models.responses.ImageResponse;

import java.util.Set;

@Mapper(componentModel = "spring" , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    Set<ImageResponse> mapImage(Set<Image> images);

    Set<Image> mapImages(Set<ImageRequest> set);
    
    Image requestToEntity(ImageRequest imageRequest);

    ImageRequest responseToRequest(ImageResponse imageResponse);

    ImageResponse entityToResponse(Image image);
}
