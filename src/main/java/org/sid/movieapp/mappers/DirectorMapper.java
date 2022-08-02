package org.sid.movieapp.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.movieapp.entities.Director;
import org.sid.movieapp.models.requests.DirectorRequest;
import org.sid.movieapp.models.responses.DirectorResponse;

import java.util.Set;

@Mapper(componentModel = "spring" , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DirectorMapper {
    DirectorMapper INSTANCE = Mappers.getMapper(DirectorMapper.class);

    Set<DirectorResponse> mapDirector(Set<Director> directors);



    Director requestToEntity(DirectorRequest directorRequest);

    DirectorResponse entityToResponse(Director director);
}
