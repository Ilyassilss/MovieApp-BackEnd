package org.sid.movieapp.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {
    private String id;
    private String imageLink ;
    private String imageType ;
    private Boolean isCover ;
    private byte[] file ;
    private String fileName ;
}
