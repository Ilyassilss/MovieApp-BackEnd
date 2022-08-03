package org.sid.movieapp.mappers;

import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.movieapp.entities.Actor;
import org.sid.movieapp.models.requests.ActorRequest;
import org.sid.movieapp.models.responses.ActorResponse;

@Mapper(componentModel = "spring" , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ActorMapper {
    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    Set<ActorResponse> mapActor(Set<Actor> actors);

    Set<Actor> mapActors(Set<ActorRequest> actors);

    Actor requestToEntity(ActorRequest actorRequest);

    ActorResponse entityToResponse(Actor actor);
}
