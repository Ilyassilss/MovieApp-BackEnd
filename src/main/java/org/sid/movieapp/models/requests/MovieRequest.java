package org.sid.movieapp.models.requests;

import java.util.Set;

import javax.validation.Valid;
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
public class MovieRequest {
    @NotNull
    @Size(min = 3,max = 100)
    private String title ;

    @NotNull
    @Size(min = 3 , max = 255)
    private String description ;

    @NotNull
    @Valid
    private DirectorRequest director ;

    @NotNull
    private Set<ActorRequest> actors ;
    private Set<ImageRequest> images ;

    @NotNull
    private Set<CategoryRequest> categories ;
}
