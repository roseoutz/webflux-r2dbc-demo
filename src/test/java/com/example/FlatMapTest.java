package com.example;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FlatMapTest {

    @Test
    public void fmTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, 10)
                .flatMap(this::sleepAndWrap)
                .map(i -> i * 10)
                .subscribe(System.out::println);

        latch.await(10000L, TimeUnit.MILLISECONDS);
    }

    private Mono<Integer> sleepAndWrap(int i) {
        return Mono.just(i)
                .publishOn(Schedulers.parallel())
                .map(j -> {

                    try {
                        long delayTime = (long) (Math.random()* 10000) % 10 * 1000;
                        Thread.sleep(delayTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return j;
                });
    }


}
