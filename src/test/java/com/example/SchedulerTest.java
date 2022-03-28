package com.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

public class SchedulerTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void schedulerTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, 10)
                .map(elem -> {
                    logger.info("map 1: {} + 10", elem);
                    return elem + 10;
                })
                .map(elem -> {
                    logger.info("///////////map 2: {} + 10", elem);
                    return elem + 10;
                })
                .subscribe();

        latch.await();
    }

    @Test
    public void schedulerPublishOnTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, 10)
                .map(elem -> {
                    logger.info("map 1: {} + 10", elem);
                    return elem + 10;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(elem -> {
                    logger.info("///////////map 2: {} + 10", elem);
                    return elem + 10;
                })
                .subscribe();

        latch.await();


    }

    @Test
    public void schedulerSubscribeOnTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, 10)
                .map(elem -> {
                    logger.info("map 1: {} + 10", elem);
                    return elem + 10;
                })
                .map(elem -> {
                    logger.info("///////////map 2: {} + 10", elem);
                    return elem + 10;
                })
                .subscribeOn(Schedulers.newSingle("sub333"))
                .subscribe();

        latch.await();

    }

    @Test
    public void schedulerCombineTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, 10)
                .map(elem -> {
                    logger.info("map 1: {} + 10", elem);
                    return elem + 10;
                })
                .publishOn(Schedulers.newSingle("pub333"))
                .map(elem -> {
                    logger.info("///////////map 2: {} + 10", elem);
                    return elem + 10;
                })
                .subscribeOn(Schedulers.newSingle("sub333"))
                .subscribe();

        latch.await();

    }
}
