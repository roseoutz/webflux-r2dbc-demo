package com.example;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class GraphqlConnectorTest {

    String queryName;

    // server to server
    class GraphqlConnector {

        protected WebClient getConnector() {
            return WebClient.create();
        }

        protected String convertToQuery(String query, Map<String, String> variables) {
            return null;
        }

        public Mono<?> monoPost(String credential, String query, Map<String, String> variables, Class<?> response) {
            return getConnector()
                    .post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", credential)
                    .bodyValue(convertToQuery(query, variables))
                    .retrieve()
                    .bodyToMono(response);
        }

        public Flux<?> fluxPost(String credential, String query, Map<String, String> variables, Class<?> response) {
            return getConnector()
                    .post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", credential)
                    .bodyValue(convertToQuery(query, variables))
                    .retrieve()
                    .bodyToFlux(response);
        }

    }


}
