package org.sid.movieapp.models.responses;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MovieResponse {

	private Long id;
	
	private String title;

	private String description;

	private DirectorResponse director;

	private Set<ActorResponse> actors;

	private Set<ImageResponse> images;

	private Set<CategoryResponse> categories;
}