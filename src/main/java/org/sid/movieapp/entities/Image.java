package org.sid.movieapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class Image {
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid" ,strategy = "uuid2")
    private String id ;
    private String imageLink ;
    private String imageType ;

    private Boolean isCover ;

    @Lob // Binary data
    private byte[] file ;
    private String fileName ;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie ;
}
