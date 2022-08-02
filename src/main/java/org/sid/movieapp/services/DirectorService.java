package org.sid.movieapp.services;

import org.sid.movieapp.entities.Director;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.DirectorRequest;
import org.sid.movieapp.models.responses.DirectorResponse;

import java.util.Set;

public interface DirectorService {
    DirectorResponse add(DirectorRequest directorRequest) throws AlreadyExistsException;

    Set<DirectorResponse> get() ;

    DirectorResponse get(Long id) throws NotFoundException;

    DirectorResponse update(Long id , DirectorRequest directorRequest) throws NotFoundException;

    void delete(Long id );
}
