package com.example.movie;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.integration.http.dsl.Http;

import java.io.File;
import java.net.URI;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(MovieProperties.class)
public class MovieIntegrationConfiguration {

    private MovieProperties movieProperties;
    private MovieConverter movieConverter;

    @Bean
    public IntegrationFlow fileFlow() {

        return IntegrationFlows.from(Files.inboundAdapter(new File(this.movieProperties.getDirectory()))
                .preventDuplicates(this.movieProperties.getPreventDuplicates())
                .patternFilter(this.movieProperties.getFilePattern()),
                        e -> e.poller(Pollers.fixedDelay(this.movieProperties.getFixedDelay()))
                )

                // splits the file contents into lines and marks the start and end of the file
                .split(Files.splitter().markers())
                // trim the first and the last part of the file content, which is generated by `markers()`
                .filter(p -> !(p instanceof FileSplitter.FileMarker))

                .transform(Transformers.converter(this.movieConverter))
                .transform(Transformers.toJson())
                .handle(Http.outboundChannelAdapter(URI.create(this.movieProperties.getRemoteService())).httpMethod(HttpMethod.POST))
                .get();
    }
}