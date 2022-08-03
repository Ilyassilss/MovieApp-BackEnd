package org.sid.movieapp.mappers;

import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.movieapp.entities.Image;
import org.sid.movieapp.models.requests.ImageRequest;
import org.sid.movieapp.models.responses.ImageResponse;

@Mapper(componentModel = "spring" , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    Set<ImageResponse> mapImage(Set<Image> images);

    Image requestToEntity(ImageRequest imageRequest);

    ImageRequest responseToRequest(ImageResponse imageResponse);

    ImageResponse entityToResponse(Image image);

	Set<Image> mapImages(Set<ImageRequest> images);
}
