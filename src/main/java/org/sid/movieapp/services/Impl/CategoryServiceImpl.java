package org.sid.movieapp.services.Impl;

import org.sid.movieapp.entities.Category;
import org.sid.movieapp.exceptions.AlreadyExistsException;
import org.sid.movieapp.exceptions.NotFoundException;
import org.sid.movieapp.mappers.CategoryMapper;
import org.sid.movieapp.models.requests.CategoryRequest;
import org.sid.movieapp.models.responses.CategoryResponse;
import org.sid.movieapp.repositories.CategoryRepository;
import org.sid.movieapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository ;



    @Override
    public CategoryResponse add(CategoryRequest categoryRequest) throws AlreadyExistsException {
        Optional<Category> findCategory = categoryRepository.findByName(categoryRequest.getName());
        if(findCategory.isPresent())
            throw new AlreadyExistsException(categoryRequest.getName(),Category.class.getSimpleName());

        return CategoryMapper.INSTANCE.entityToResponse(categoryRepository.save(CategoryMapper.INSTANCE.requestToEntity(categoryRequest)));
    }

    @Override
    public Set<CategoryResponse> get() {
        return CategoryMapper.INSTANCE.mapCategory(categoryRepository.findAll().stream().collect(Collectors.toSet()));
    }

    @Override
    public CategoryResponse get(Long id) throws NotFoundException {
        Optional<Category> findCategory = categoryRepository.findById(id);
        if(!findCategory.isPresent())
            throw new NotFoundException(Category.class.getSimpleName());

        return CategoryMapper.INSTANCE.entityToResponse(findCategory.get());
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) throws NotFoundException {
        Category category = categoryRepository.findById(id).get();
        if(category==null)
            throw new NotFoundException(Category.class.getSimpleName());

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        return CategoryMapper.INSTANCE.entityToResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
