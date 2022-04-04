package com.example;

import com.example.entity.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.UUID;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class R2dbcTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private R2dbcEntityTemplate template;

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    void contextLoads() throws InterruptedException {
        insertByClient();
        insertByTemplate();
        logger.info("start");
        queryByTemplate();

        logger.info("after query template");

        queryByDatabaseClient();

        logger.info("after query client");

        Thread.sleep(10000L);

    }

    private void insertByTemplate() {
        String oid = UUID.randomUUID().toString();
        User user = new User();
        user.setOid(oid);
        user.setUserId("test");
        user.setUsername("test");
        user.setPassword("test");
        user.setCellPhone("00000000000");
        user.setIsNew(true);
        template.insert(user)
                .doOnNext(entity -> logger.info("in template insert : {}", entity.toString()))
                .doOnError(throwable -> logger.error("error cause in ibt", throwable))
                .subscribe();
    }

    private void insertByClient() {
        String oid = UUID.randomUUID().toString();
        databaseClient.sql("insert into bds_cm_user values (:oid, :userId, :username, :password, :cellPhone)")
                .bind("oid", oid)
                .bind("userId", "test2")
                .bind("username", "test2")
                .bind("password", "123456")
                .bind("cellPhone", "0000000000")
                .fetch()
                .rowsUpdated()
                .doOnNext(entity -> logger.info("in database client insert : {}", entity.toString()))
                .doOnError(throwable -> logger.error("error cause in ibc", throwable))
                .subscribe();
    }

    private void queryByTemplate() {
        template.select(User.class)
                .from("user")
                .matching(Query.query(Criteria.where("user_id").is("test")))
                .one()
                .log()
                .doOnNext(entity -> logger.info("in template select : {}", entity.toString()))
                .doOnError(throwable -> logger.error("error cause in qbt", throwable))
                .subscribe();
    }

    private void queryByDatabaseClient() {
        databaseClient.sql("select * from bds_cm_user where user_id = :userId")
                .bind("userId", "test2")
                .fetch()
                .one()
                .log()
                .doOnNext(entity -> logger.info("in database client select : {}", entity.toString()))
                .doOnError(throwable -> logger.error("error cause in qdc", throwable))
                .subscribe();
    }
}
