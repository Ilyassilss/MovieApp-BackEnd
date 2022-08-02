package org.sid.movieapp.services.Impl;

import org.sid.movieapp.entities.Director;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.mappers.DirectorMapper;
import org.sid.movieapp.mappers.MovieMapper;
import org.sid.movieapp.models.requests.DirectorRequest;
import org.sid.movieapp.models.responses.DirectorResponse;
import org.sid.movieapp.repositories.DirectorRepository;
import org.sid.movieapp.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    private DirectorRepository directorRepository ;


    @Override
    public DirectorResponse add(DirectorRequest directorRequest) throws AlreadyExistsException {
        Optional<Director> findDirector = directorRepository.findByName(directorRequest.getName());
        if(findDirector.isPresent()){
            throw new AlreadyExistsException(directorRequest.getName(),Director.class.getSimpleName());
        }
        return DirectorMapper.INSTANCE.entityToResponse(directorRepository.save(DirectorMapper.INSTANCE.requestToEntity(directorRequest)));
    }

    @Override
    public Set<DirectorResponse> get() {
        return DirectorMapper.INSTANCE.mapDirector(directorRepository.findAll().stream().collect(Collectors.toSet())) ;
    }

    @Override
    public DirectorResponse get(Long id) throws NotFoundException {
        Optional<Director> findDirector = directorRepository.findById(id);
        if(!findDirector.isPresent())
            throw new NotFoundException(Director.class.getSimpleName());
        return DirectorMapper.INSTANCE.entityToResponse(directorRepository.findById(id).get()) ;
    }

    @Override
    public DirectorResponse update(Long id, DirectorRequest directorRequest) throws NotFoundException {
        Director director = directorRepository.findById(id).get();
        if(director==null)
            throw new NotFoundException(Director.class.getSimpleName());

        director.setName(directorRequest.getName());
        director.setPhone(directorRequest.getPhone());
        return DirectorMapper.INSTANCE.entityToResponse(directorRepository.save(director)) ;
    }

    @Override
    public void delete(Long id) {
        directorRepository.deleteById(id);
    }
}
