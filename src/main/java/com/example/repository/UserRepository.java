package com.example.repository;

import com.example.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, String> {

    Mono<Void> deleteByOid(String oid);

    Mono<User> findByUserId(String userId);

    Flux<User> findBy(Pageable pageable);

    Flux<User> findByUserId(String userId, Pageable pageable);
}
