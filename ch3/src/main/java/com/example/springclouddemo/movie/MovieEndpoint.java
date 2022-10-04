package com.example.springclouddemo.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@MessageEndpoint
public class MovieEndpoint {
    private static final Logger log = LoggerFactory.getLogger(MovieEndpoint.class);

    @ServiceActivator
    public void processMovie(Movie movie, @Headers Map<String, Object> headers) throws Exception {

        log.info("Movie" + movie);
    }

    @ServiceActivator
    public void processMovies(Movie[] movies, @Headers Map<String, Object> headers) throws Exception {

        for (Movie movie: movies) { log.info("Movies" + movies); }
    }
}