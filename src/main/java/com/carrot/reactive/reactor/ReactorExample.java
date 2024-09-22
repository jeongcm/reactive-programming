package com.carrot.reactive.reactor;

public class ReactorExample implements ApplicationRunner {

    public static void main(String[] args) {
        Flux<String> sequence = Flux.just("HELLO", "JEONG");
        sequence.map(String::toLowerCase).subscribe(System.out::println);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
