package org.sid.movieapp.services.Impl;

import org.sid.movieapp.entities.Actor;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.mappers.ActorMapper;
import org.sid.movieapp.models.requests.ActorRequest;
import org.sid.movieapp.models.responses.ActorResponse;
import org.sid.movieapp.repositories.ActorRepository;
import org.sid.movieapp.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository ;



    @Override
    public ActorResponse add(ActorRequest actorRequest) throws AlreadyExistsException{
        Optional<Actor> findActor = actorRepository.findByFirstNameAndLastName(actorRequest.getFirstName(),actorRequest.getLastName());
        if(findActor.isPresent())
            throw new AlreadyExistsException(actorRequest.getLastName() , Actor.class.getSimpleName());
        return ActorMapper.INSTANCE.entityToResponse(actorRepository.save(ActorMapper.INSTANCE.requestToEntity(actorRequest)));
    }

    @Override
    public ActorResponse get(Long id) throws  NotFoundException{
        Optional<Actor> findActor = actorRepository.findById(id);
        if(findActor.isPresent())
            throw new NotFoundException(Actor.class.getSimpleName());
        return ActorMapper.INSTANCE.entityToResponse(findActor.get());
    }

    @Override
    public Set<ActorResponse> get() {
        return ActorMapper.INSTANCE.mapActor(actorRepository.findAll().stream().collect(Collectors.toSet())) ;
    }

    @Override
    public ActorResponse update(Long id, ActorRequest actorRequest) throws  NotFoundException {
        Actor actor = actorRepository.findById(id).get();
        if(actor == null)
            throw new NotFoundException(Actor.class.getSimpleName());

        actor.setFirstName(actorRequest.getFirstName());
        actor.setLastName(actorRequest.getLastName());
        actor.setAge(actorRequest.getAge());
        actor.setPhotoLink(actorRequest.getPhotoLink());

        return ActorMapper.INSTANCE.entityToResponse(actorRepository.save(actor)) ;
    }

    @Override
    public void delete(Long id) {
        actorRepository.deleteById(id);
    }
}
