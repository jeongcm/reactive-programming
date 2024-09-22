package com.carrot.reactive.reactor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;

public class ReactorExample implements ApplicationRunner {

    public static void main(String[] args) {
        Flux<String> sequence = Flux.just("HELLO", "JEONG");
        sequence.map(String::toLowerCase).subscribe(System.out::println);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
