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
//    private static final Logger log = LoggerFactory.getLogger(MovieEndpoint.class);

    @Autowired
    private MovieService movieService;

    @ServiceActivator
    public Message<String> process(File input, @Headers Map<String, Object> headers) throws Exception {

        FileInputStream in = new FileInputStream(input);

        String movies = movieService.format(new String(StreamUtils.copyToByteArray(in)));

        in.close();

        return MessageBuilder.withPayload(movies)
                .setHeader("name", input.getName())
                .setHeader("Content-Type", "application/json")
                .build();
    }
}