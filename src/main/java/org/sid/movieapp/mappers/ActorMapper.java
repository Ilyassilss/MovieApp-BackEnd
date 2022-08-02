package org.sid.movieapp.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.movieapp.entities.Actor;
import org.sid.movieapp.models.requests.ActorRequest;
import org.sid.movieapp.models.responses.ActorResponse;

import java.util.Set;

@Mapper(componentModel = "spring" , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ActorMapper {
    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    Set<ActorResponse> mapActor(Set<Actor> actors);

    Actor requestToEntity(ActorRequest actorRequest);

    ActorResponse entityToResponse(Actor actor);
}
