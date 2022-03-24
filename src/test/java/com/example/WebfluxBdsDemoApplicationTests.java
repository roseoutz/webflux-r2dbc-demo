package com.example;

import com.example.entity.Comment;
import com.example.entity.QUser;
import com.example.entity.User;
import com.example.repository.CommentRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.example.entity.QComment.comment;
import static com.example.entity.QUser.user;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class WebfluxBdsDemoApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private String getOid() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void queryDslUserTest() throws InterruptedException {

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
                .subscribe();

        Thread.sleep(4000L);
    }

    @Test
    public void querydslTest() throws InterruptedException {
        String oid = getOid();
        String parentOid = getOid();
        String rootOid = getOid();

        Comment commentDto = new Comment();
        commentDto.setOid(oid);
        commentDto.setDeleted(false);
        commentDto.setContent("hahahahaha");
        commentDto.setParentOid(parentOid);
        commentDto.setRootOid(rootOid);
        commentDto.setUserId("test");
        commentDto.setIsNew(true);

        commentRepository.save(commentDto)
                .flatMap(entity ->
                    commentRepository.query(sqlQuery -> sqlQuery
                                .select(comment)
                                .from(comment)
                                .where(comment.oid.eq(entity.getOid()))
                    ).one())
                .doOnNext(dslEntity -> {
                    logger.info(dslEntity.toString());
                })
                .subscribe();


        Thread.sleep(4000L);
    }

}
