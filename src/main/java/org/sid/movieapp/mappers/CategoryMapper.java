package org.sid.movieapp.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sid.movieapp.entities.Category;
import org.sid.movieapp.models.requests.CategoryRequest;
import org.sid.movieapp.models.responses.CategoryResponse;

import java.util.Set;

@Mapper(componentModel = "spring" , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Set<CategoryResponse> mapCategory(Set<Category> categories);

    Category requestToEntity(CategoryRequest categoryRequest);

    CategoryResponse entityToResponse(Category category);
}
