package com.carrot.reactive.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class JustOrEmptyExample implements ApplicationRunner {

    public static void main(String[] args) {
        // justOrEmpty 는  null 을 보내도 nullPointException을 보내지 않음.
        Mono.justOrEmpty(null)
                .subscribe(data -> {},
                        error -> {},
                        () -> log.info("# onComplete"));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
