package org.sid.movieapp.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private String id ;
    private String imageType ;
    private String imageName ;
    private String imageLink ;
    private Long imageSize ;
    private String fileName ;
    private byte[] file ;
}
