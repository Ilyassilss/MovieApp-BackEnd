package org.sid.movieapp.models.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageRequest {
    private String id;
    private String imageLink ;
    private String imageType ;
    private Boolean isCover ;
    private byte[] file ;
    private String fileName ;
}
