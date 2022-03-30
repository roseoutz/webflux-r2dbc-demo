package com.example.controller;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("/api/v1/user/")
public class UserController {

    private final UserService userService;

    @GetMapping("user/{oid}")
    public Mono<ResponseEntity<UserResponse>> get(@PathVariable(name = "oid", required = false) String oid) {
        return userService.get(oid)
                .map(ResponseEntity::ok);
    }

    @GetMapping("users/mono")
    public Mono<ResponseEntity<List<UserResponse>>> listMono(
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "pageSize", required = false) String pageSize,
            @RequestParam(name = "userId", required = false) String userId
    ) {
        Pageable pageable = PageRequest.of(page == null ? 1 : Integer.parseInt(page), pageSize == null ? 10 : Integer.parseInt(pageSize));
        return userService.listMono(userId, pageable)
                .map(ResponseEntity::ok);
    }

    @GetMapping("users/flux")
    public Flux<ResponseEntity<UserResponse>> listFlux(
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "pageSize", required = false) String pageSize,
            @RequestParam(name = "userId", required = false) String userId
    ) {
        Pageable pageable = PageRequest.of(page == null ? 1 : Integer.parseInt(page), pageSize == null ? 10 : Integer.parseInt(pageSize));
        return userService.listFlux(userId, pageable)
                .map(ResponseEntity::ok);
    }

    @PostMapping("user")
    public Mono<ResponseEntity<UserResponse>> add(@RequestBody UserRequest userRequest) {
        return userService.add(userRequest)
                .doOnNext(userResponse -> log.info("controller"))
                .map(ResponseEntity::ok);
    }

    @PutMapping("user")
    public Mono<ResponseEntity<UserResponse>> put(@RequestBody UserRequest userRequest) {
        return userService.update(userRequest)
                .switchIfEmpty(Mono.error(new Exception("USER NOT FOUND")))
                .map(ResponseEntity::ok);
    }

}
