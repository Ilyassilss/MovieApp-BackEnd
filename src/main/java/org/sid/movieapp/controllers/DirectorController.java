package org.sid.movieapp.controllers;

import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.DirectorRequest;
import org.sid.movieapp.models.responses.DirectorResponse;
import org.sid.movieapp.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/api/director")
public class DirectorController {
    @Autowired
    private DirectorService directorService ;

    @PostMapping
    public ResponseEntity<DirectorResponse> add(@RequestBody DirectorRequest directorRequest) throws AlreadyExistsException {
        return new ResponseEntity<DirectorResponse>(directorService.add(directorRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<DirectorResponse>> get(){
        return new ResponseEntity<Set<DirectorResponse>>(directorService.get(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> get(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<DirectorResponse>(directorService.get(id),HttpStatus.OK);
    }
}
