package com.example.graphql;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.dto.UserSearch;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Controller
public class UserGqlController {

    @Autowired
    private UserService userService;

    @QueryMapping
    public Mono<UserResponse> userGet(@Argument String oid) {
        return userService.get(oid);
    }

    @QueryMapping
    public Mono<List<UserResponse>> userListMono(@Argument UserSearch userSearch) {
        Pageable pageable = PageRequest.of(userSearch.getPage(), userSearch.getPageSize() == 0 ? 10 : userSearch.getPageSize());
        return userService.listMono(userSearch.getUserId(), pageable);
    }

    @QueryMapping
    public Flux<UserResponse> userListFlux(@Argument UserSearch userSearch) {
        Pageable pageable = PageRequest.of(userSearch.getPage(), userSearch.getPageSize() == 0 ? 10 : userSearch.getPageSize());
        return userService.listFlux(userSearch.getUserId(), pageable);
    }

    @MutationMapping
    public Mono<UserResponse> userAdd(@Argument UserRequest userRequest) {
        return userService.add(userRequest)
                .doOnNext(userResponse -> log.info("controller"));
    }

    @MutationMapping
    public Mono<UserResponse> userUpdate(@Argument UserRequest userRequest) {
        return userService.update(userRequest)
                .switchIfEmpty(Mono.error(new Exception("USER NOT FOUND")));
    }

}
