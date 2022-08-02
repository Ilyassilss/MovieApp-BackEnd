package org.sid.movieapp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "movie")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String title ;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timestamp ;

    @ManyToOne(fetch = FetchType.EAGER)
    private Director director ;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

    @OneToMany(mappedBy = "movie" , cascade = CascadeType.ALL)
    private Set<Image> images ;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name="movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories ;

    @PrePersist
    private void onCreate(){
        this.timestamp = new Date();
    }

}
