package org.sid.movieapp.controllers;

import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.ActorRequest;
import org.sid.movieapp.models.responses.ActorResponse;
import org.sid.movieapp.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/actor")
public class ActorCategory {
    @Autowired
    private ActorService actorService ;

    @PostMapping
    public ResponseEntity<ActorResponse> add(@RequestBody ActorRequest actorRequest) throws AlreadyExistsException {
        return new ResponseEntity<ActorResponse>
                (actorService.add(actorRequest) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<ActorResponse>> getAll(){
        return new ResponseEntity<Set<ActorResponse>>
                (actorService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorResponse> get(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<ActorResponse>
                (actorService.get(id),HttpStatus.OK);
    }
}
