package com.example.repository;

import com.example.entity.Post;
import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends QuerydslR2dbcRepository<Post, String> {
}
