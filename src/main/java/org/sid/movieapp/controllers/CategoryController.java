package org.sid.movieapp.controllers;

import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.CategoryRequest;
import org.sid.movieapp.models.responses.CategoryResponse;
import org.sid.movieapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService ;

    @GetMapping
    public ResponseEntity<Set<CategoryResponse>> getAll(){
        return new ResponseEntity<Set<CategoryResponse>>
                (categoryService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<CategoryResponse>
                (categoryService.get(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> add(@RequestBody CategoryRequest categoryRequest) throws AlreadyExistsException {
        return new ResponseEntity<CategoryResponse>
                (categoryService.add(categoryRequest),HttpStatus.CREATED);
    }
}
