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
@Table(name = "category")
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String name ;
    private String description ;
    @ManyToMany
    @JsonBackReference
    private List<Movie> movies ;
}
