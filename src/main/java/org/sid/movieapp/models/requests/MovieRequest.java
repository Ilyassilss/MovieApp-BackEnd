package org.sid.movieapp.models.requests;

import lombok.Builder;
import lombok.Data;
import org.sid.movieapp.entities.Actor;
import org.sid.movieapp.entities.Image;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
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
