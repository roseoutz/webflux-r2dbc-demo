package com.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public class ReactorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //@Test
    public void fluxJustTest() {
        List<String> arr = List.of("apple", "banana", "pineapple", "grape");

        Flux.just(arr)
                .doOnNext(fruit -> logger.info(String.valueOf(fruit)))
                .log()
                .subscribe();
    }

    //@Test
    public void fluxRangeTest() {
        Flux.range(1, 10)
                .doOnNext(element -> logger.info(String.valueOf(element)))
                .log()
                .subscribe();
    }

    //@Test
    public void fluxIteratorTest() {
        String[] fruits = {"apple", "banana", "pineapple", "grape"};

        Flux<String> iterator = Flux.fromIterable(List.of(fruits))
                .log();

        Flux<String> array = Flux.fromArray(fruits)
                .log();

        Flux<String> stream = Flux.fromStream(Stream.of(fruits))
                .log();

        logger.info("==================iterator start==============================");
        iterator.subscribe();
        logger.info("==================array start=================================");
        array.subscribe();
        logger.info("=================stream start=================================");
        stream.subscribe();
    }

    //@Test
    public void fluxEmptyTest() {
        Flux.empty()
                .doOnNext(elem -> logger.info((String) elem))
                .log()
                .subscribe();
    }

    //@Test
    public void fluxSample() {
        // get Fruits
        List<String> vegetable = List.of("tomato", "lettuce", "pumpkin");

        List<String> berries = List.of("strawberry", "blueberry", "cranberry");

        Flux.fromIterable(vegetable)
                .mergeWith(Flux.fromIterable(berries))
                .log()
                .filter(elem -> elem.contains("a"))
                .log()
                .subscribe();
    }

    //@Test
    public void monoJustTest() {
        List<String> arr = List.of("apple", "banana", "pineapple", "grape");

        Mono.just(arr)
                .log()
                .subscribe();
    }

    //@Test
    public void monoEmptyTest() {
        Mono.empty()
                .log()
                .subscribe();
    }

    //@Test
    public void coldPublisherTest() throws InterruptedException {
        // 1 - 5까지 숫자를 1초 간격으로 발행한다.
        Flux<Integer> intFlux = Flux.range(1, 5).delayElements(Duration.ofSeconds(1));

        intFlux.subscribe(elem -> logger.info("구독자 1 ==== " + elem ));
        Thread.sleep(1000L);
        intFlux.subscribe(elem -> logger.info("구독자 2 ==== " + elem ));
        Thread.sleep(1000L);
        intFlux.subscribe(elem -> logger.info("구독자 3 ==== " + elem ));
        Thread.sleep(10000L);
    }

    //@Test
    public void hotPublisherTest() throws InterruptedException {
        // publish() 호출로 hot으로 전환
        ConnectableFlux<Integer> intFlux = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(100))
                .filter(i -> i < 100)
                .switchIfEmpty(Flux.empty())
                .log()
                .publish();

        intFlux.connect();

        intFlux.subscribe(elem -> System.out.println("구독자 1 ==== " + elem ));
        Thread.sleep(200);
        intFlux.subscribe(elem -> System.out.println("구독자 2 ==== " + elem ));
        Thread.sleep(200);
        intFlux.subscribe(elem -> System.out.println("구독자 3 ==== " + elem ));
        Thread.sleep(10000L);
    }


    //@Test
    public void threadTest() {
        Flux.range(1, 100)
                .flatMap(i -> Mono.just(i)
                        .publishOn(Schedulers.parallel())
                        .flatMap(mi -> {
                            logger.info("in flatMap mono : " + mi);
                            return Mono.just(mi);
                        })
                        .map(mi -> {
                            logger.info("in map mono : " + mi);
                            return mi;
                        })
                )
                .doOnNext(i -> logger.info("in flux : " + i))
                .subscribe();
    }
}
