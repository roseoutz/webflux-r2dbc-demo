package com.example.repository;

import com.example.entity.Post;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends R2dbcRepository<Post, String> {
    Mono<Post> findByOid(String oid);
}
