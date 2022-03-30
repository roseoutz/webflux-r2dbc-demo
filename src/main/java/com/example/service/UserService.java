package com.example.service;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<UserResponse> get(String oid);

    Mono<UserResponse> add(UserRequest userRequest);

    Mono<UserResponse> update(UserRequest userRequest);

    Mono<Void> delete(String oid);

    Mono<List<UserResponse>> listMono(String userId, Pageable pageable);

    Flux<UserResponse> listFlux(String userId, Pageable pageable);
}

