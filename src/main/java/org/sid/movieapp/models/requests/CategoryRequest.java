package org.sid.movieapp.models.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotNull
    @Size(min = 3 , max = 50)
    private String name ;

    @NotNull
    @Size(min = 3 , max = 255)
    private String description ;
}
