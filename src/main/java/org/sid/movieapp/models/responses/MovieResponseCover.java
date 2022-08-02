package org.sid.movieapp.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;
@Data
@Builder
public class MovieResponseCover {
    private Long id ;
    private String title ;
    private String description ;
    private Date timestamp ;
    private DirectorResponse director ;
    private ImageResponse cover  ;
    private Set<ActorResponse> actors ;
    private Set<CategoryResponse> categories ;
}
