package com.example.springclouddemo.movie;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("META-INF/spring/movie-app-integration.xml")
public class MovieConfiguration {

}
