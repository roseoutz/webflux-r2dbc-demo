package com.example.controller;

import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.service.PostService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
// @RequiredArgsConstructor
@RestController("/api/v1/post/")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("post/{oid}")
    public Mono<ResponseEntity<PostResponse>> get(@PathVariable(name = "oid", required = false) String oid) {
        return postService.get(oid)
                .map(ResponseEntity::ok);
    }

    @GetMapping("posts/mono")
    public Mono<ResponseEntity<List<PostResponse>>> listMono(
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "pageSize", required = false) String pageSize
    ) {
        Pageable pageable = PageRequest.of(page == null ? 1 : Integer.parseInt(page), pageSize == null ? 10 : Integer.parseInt(pageSize));
        return postService.listMono(pageable)
                .map(ResponseEntity::ok);
    }

    @GetMapping("posts/flux")
    public Flux<ResponseEntity<PostResponse>> listFlux(
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "pageSize", required = false) String pageSize
    ) {
        Pageable pageable = PageRequest.of(page == null ? 1 : Integer.parseInt(page), pageSize == null ? 10 : Integer.parseInt(pageSize));
        return postService.listFlux(pageable)
                .map(ResponseEntity::ok);
    }

    @PostMapping("post")
    public Mono<ResponseEntity<PostResponse>> add(@RequestBody PostRequest postRequest) {
        return postService.add(postRequest)
                .doOnNext(userResponse -> log.info("controller"))
                .map(ResponseEntity::ok);
    }


}
