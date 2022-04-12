package com.example;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GraphqlConnectorTest {

    String queryName;


    class GraphqlConnector {



        public Mono<Object> monoConnector(String data) {
            return null;
        }

        public Flux<Object> fluxConnector(String data) {
            return null;
        }
    }


}
