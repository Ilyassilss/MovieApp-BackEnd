package org.sid.movieapp.services;

import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.ActorRequest;
import org.sid.movieapp.models.responses.ActorResponse;

import java.util.Set;

public interface ActorService {
    ActorResponse add(ActorRequest actorRequest) throws AlreadyExistsException;

    ActorResponse get(Long id) throws  NotFoundException;

    Set<ActorResponse> get() ;

    ActorResponse update(Long id , ActorRequest actorRequest) throws  NotFoundException;

    void delete(Long id);
}
