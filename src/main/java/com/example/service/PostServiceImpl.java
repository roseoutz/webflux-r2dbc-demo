package com.example.service;

import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import com.example.entity.Post;
import com.example.entity.QComment;
import com.example.entity.QPost;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<PostResponse> get(String oid) {
        return postRepository.query(sqlQuery ->
                sqlQuery
                        .select(postRepository.entityProjection())
                        .from(QPost.post)
                        .where(QPost.post.oid.eq(oid)))
                .one()
                .map(post -> modelMapper.map(post, PostResponse.class));
    }

    @Override
    public Mono<PostResponse> add(PostRequest postRequest) {
        Post post = modelMapper.map(postRequest, Post.class);
        return postRepository.save(post)
                .map(savedEntity -> modelMapper.map(savedEntity, PostResponse.class));
    }

    @Override
    public Mono<List<PostResponse>> listMono(Pageable pageable) {
        return null;
    }

    @Override
    public Flux<PostResponse> listFlux(Pageable pageable) {
        return null;
    }
}
