package org.sid.movieapp;

import org.sid.movieapp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieAppApplication implements CommandLineRunner {

    @Autowired
    private ImageService imageService ;

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {
		imageService.init();
	}
}
