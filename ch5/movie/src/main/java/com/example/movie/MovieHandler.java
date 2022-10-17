package com.example.movie;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
@AllArgsConstructor
public class MovieHandler {
    private RestTemplate restTemplate;

    public void process(Movie movie){

        log.debug("Processing: {}", movie);
        ResponseEntity<Object> response = this.restTemplate.postForEntity("http://movie-web/v1/movie",movie,Object.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) { log.debug("processed"); }
        else { log.warn("Take a look of the logs..."); }
    }
}