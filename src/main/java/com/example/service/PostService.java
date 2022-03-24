package com.example.service;

import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostService {

    Mono<PostResponse> get(String oid);

    Mono<PostResponse> add(PostRequest postRequest);

    Mono<List<PostResponse>> listMono(Pageable pageable);

    Flux<PostResponse> listFlux(Pageable pageable);

}
