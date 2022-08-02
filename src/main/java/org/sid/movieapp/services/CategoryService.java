package org.sid.movieapp.services;

import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.models.requests.CategoryRequest;
import org.sid.movieapp.models.responses.CategoryResponse;

import java.util.Set;

public interface CategoryService {
    CategoryResponse add(CategoryRequest categoryRequest) throws AlreadyExistsException ;

    Set<CategoryResponse> get();

    CategoryResponse get(Long id)throws NotFoundException;

    CategoryResponse update(Long id , CategoryRequest categoryRequest) throws NotFoundException;

    void delete(Long id);
}
