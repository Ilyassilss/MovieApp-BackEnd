package org.sid.movieapp.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActorResponse {
    private Long id ;
    private String firstName ;
    private String lastName ;
    private int age ;
}
