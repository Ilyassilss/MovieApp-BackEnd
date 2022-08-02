package org.sid.movieapp.models.requests;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import org.sid.movieapp.entities.Movie;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class ActorRequest {
    @NotNull
    @Size(min = 3 , max = 50)
    private String firstName ;

    @NotNull
    @Size(min = 3 , max = 50)
    private String lastName ;

    @NotNull
    private int age ;
    private String photoLink ;

}
