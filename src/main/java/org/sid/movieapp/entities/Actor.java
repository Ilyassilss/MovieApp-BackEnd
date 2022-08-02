package org.sid.movieapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "actor")
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class Actor {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String firstName ;
    private String lastName ;
    private int age ;
    private String photoLink ;
    @ManyToMany
    @JsonBackReference
    private List<Movie> movies;
}
