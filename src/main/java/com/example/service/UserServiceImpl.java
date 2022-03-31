package com.example.service;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.entity.User;
import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    protected User toEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    protected UserResponse toDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public Mono<UserResponse> get(String oid) {
        return userRepository.findById(oid)
                .switchIfEmpty(Mono.error(new Exception("USER NOT FOUND")))
                .flatMap(entity -> Mono.just(toDto(entity)));
    }

    @Override
    @Transactional
    public Mono<UserResponse> add(final UserRequest userRequest) {
        return Mono.just(userRequest)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(userResponse -> log.info("after create publisher"))
                .flatMap(dto -> checkRegisterUser(dto.getUserId())
                        .publishOn(Schedulers.boundedElastic())
                        .doOnNext(userResponse -> log.info("after check user"))
                        .flatMap(isRegister -> {
                            if (isRegister) {
                                return Mono.error(new Exception("ALREADY REGISTER USER"));
                            }

                            return Mono.just(dto);
                        })
                        .doOnNext(userResponse -> log.info("after check user22222"))
                        .flatMap(user -> {
                            User entity = toEntity(user);
                            entity.setIsNew(true);
                            entity.setOid(UUID.randomUUID().toString());
                            return userRepository.save(entity);
                        })
                        .doOnNext(userResponse -> log.info("after user save"))
                        .map(this::toDto)
                )
                .onErrorResume(throwable -> Mono.error(new Exception(throwable)));
    }

    private Mono<Boolean> checkRegisterUser(String userId) {
        return userRepository.findByUserId(userId)
                .doOnNext(entity -> log.info(userId + " //// " +entity.toString()))
                .map(entity-> true)
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    @Transactional
    public Mono<UserResponse> update(final UserRequest userRequest) {
        return Mono.just(userRequest)
                .filter(dto -> !Objects.isNull(dto.getOid()))
                .flatMap(dto -> userRepository.findById(dto.getOid()))
                .switchIfEmpty(Mono.error(new Exception("USER NOT FOUND")))
                .flatMap(entity -> {
                    if (!userRequest.getUserId().equals(entity.getUserId())) entity.setUserId(userRequest.getUserId());
                    if (!userRequest.getCellPhone().equals(entity.getCellPhone())) entity.setCellPhone(userRequest.getCellPhone());
                    if (!userRequest.getUsername().equals(entity.getUsername())) entity.setUsername(userRequest.getUsername());
                    if (!userRequest.getPassword().equals(entity.getPassword())) entity.setPassword(userRequest.getPassword());
                    //return entity;
                    return userRepository.save(entity);
                })
                .map(this::toDto)
                .doOnError(Mono::error);
    }

    @Override
    public Mono<Void> delete(String oid) {
        return userRepository.deleteByOid(oid);
    }

    @Override
    public Mono<List<UserResponse>> listMono(String userId, Pageable pageable) {
        return listFlux(userId, pageable)
                .collectList();
    }

    @Override
    public Flux<UserResponse> listFlux(String userId, Pageable pageable) {
        if (!Objects.isNull(userId)) {
            return userRepository.findByUserId(userId, pageable)
                    .map(this::toDto)
                    .onErrorResume(throwable -> Mono.error(new Exception(throwable)));
        }
        return userRepository.findBy(pageable)
                .map(this::toDto)
                .onErrorResume(throwable -> Mono.error(new Exception(throwable)));
    }
}
