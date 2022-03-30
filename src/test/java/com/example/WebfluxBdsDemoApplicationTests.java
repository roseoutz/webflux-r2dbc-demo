package com.example;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

import static com.example.entity.QComment.comment;
import static com.example.entity.QUser.user;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class WebfluxBdsDemoApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private UserRepository userRepository;

    private String getOid() {
        return UUID.randomUUID().toString();
    }

    @BeforeEach
    public void before() {

        for (int i = 0 ; i < 10; i++) {
            final User userDto = new User();
            userDto.setOid(getOid());
            userDto.setUserId("testest" + i);
            userDto.setUsername("testestest");
            userDto.setPassword("testestest");
            userDto.setCellPhone("2020202020");
            userDto.setIsNew(true);

            userRepository.save(userDto)
                    .subscribe();
        }
    }

    @Test
    public void queryDslUserTest() throws InterruptedException {
/*

        final User userDto = new User();
        userDto.setOid(getOid());
        userDto.setUserId("testest");
        userDto.setUsername("testestest");
        userDto.setPassword("testestest");
        userDto.setCellPhone("2020202020");
        userDto.setIsNew(true);



        userRepository.save(userDto)
                .doOnNext(entity -> logger.info(entity.toString()))
                .flatMap(entity ->
                    userRepository.query(sqlQuery ->
                            sqlQuery.select(user)
                            .from(user)
                            .where(user.userId.eq("testest"))
                    ).one()
                )
                .doOnNext(dslEntity -> {
                    logger.info(dslEntity.toString());
                })
                .subscribe();*/
        Pageable pageable = PageRequest.of(0, 10);

        userRepository.findByUserId(null, pageable)
                .doOnNext(entity -> logger.info(entity.toString()))
                .subscribe();
        Thread.sleep(10000L);
    }


}
