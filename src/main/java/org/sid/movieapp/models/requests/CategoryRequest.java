package org.sid.movieapp.models.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class CategoryRequest {
    @NotNull
    @Size(min = 3 , max = 50)
    private String name ;

    @NotNull
    @Size(min = 3 , max = 255)
    private String description ;
}
