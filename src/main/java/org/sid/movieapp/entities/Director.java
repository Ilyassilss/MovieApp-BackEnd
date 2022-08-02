package org.sid.movieapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "director")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Director {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String name ;
    private String phone ;

    @OneToMany(mappedBy = "director")
    @JsonBackReference
    private Set<Movie> movies ;
}
