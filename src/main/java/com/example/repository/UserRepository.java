package com.example.repository;

import com.example.entity.User;
import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends QuerydslR2dbcRepository<User, String> {

    Mono<Void> deleteByOid(String oid);

    Mono<User> findByUserId(String userId);

    Flux<User> findBy(Pageable pageable);
}
