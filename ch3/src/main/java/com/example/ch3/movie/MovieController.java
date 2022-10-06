package com.example.ch3.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @PostMapping("/v1/movie")
    public ResponseEntity<String> movie(@RequestBody Movie body) {

        log.info("Movie: " + body);

        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/v1/movies")
    public ResponseEntity<String> movies(@RequestBody Movie[] body) {

        for (Movie movie: body) { log.info("Movie: " + movie); }

        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }
}