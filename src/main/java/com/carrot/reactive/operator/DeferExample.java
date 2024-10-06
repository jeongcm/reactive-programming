package com.carrot.reactive.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DeferExample implements ApplicationRunner {

    public static void main(String[] args) throws InterruptedException {
        log.info("# start: {}", LocalDateTime.now());
        Mono<LocalDateTime> justMono = Mono.just(LocalDateTime.now());
        Mono<LocalDateTime> deferMono = Mono.defer(() -> Mono.just(LocalDateTime.now()));

        Thread.sleep(2000);

        justMono.subscribe(data -> log.info("# onNext just 1: {}", data));
        deferMono.subscribe(data -> log.info("# onNext defer 1: {}", data));
        Thread.sleep(2000);

        justMono.subscribe(data -> log.info("# onNext just 2: {}", data));
        deferMono.subscribe(data -> log.info("# onNext defer 2: {}", data));

        // 그냥 just operator 는 Hot Publisher 이기 대문에 구독 여부와 상관없이 데이터가 emit (Hot sequence 는 emit 이 한번만 발생)
        // 따라서 구독이 발생하면 emit 된 데이터가 replay 되서 구독자에게 전달됨

        Mono.just("Hello")
                .delayElement(Duration.ofSeconds(3))
//                .switchIfEmpty(sayDefault())
                .switchIfEmpty(Mono.defer(DeferExample::sayDefault))
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(3500);
    }

    private static Mono<String> sayDefault() {
        log.info("# Say Hi");
        return Mono.just("hi");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
