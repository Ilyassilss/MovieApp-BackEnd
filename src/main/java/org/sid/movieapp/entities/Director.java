package org.sid.movieapp.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "director")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Director {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String name ;
    private String phone ;
    private String photoLink;

    @OneToMany(mappedBy = "director")
    @JsonBackReference
    private Set<Movie> movies ;
}
