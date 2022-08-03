package org.sid.movieapp.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "actor")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
