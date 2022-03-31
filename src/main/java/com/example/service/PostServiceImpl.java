package com.example.service;

import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import com.example.entity.Post;
import com.example.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Mono<PostResponse> get(String oid) {
        return postRepository.findByOid(oid)
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
