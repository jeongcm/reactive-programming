package com.carrot.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerSubscribeExample implements ApplicationRunner {

    // reactor 의 scheduler 는 비동기 프로그래밍 스레드를 관리하기 위한 역할
    // subscribeOn 은 구독 직후 실행될 스레드 지정
    // subscribeOn 이 선언되면 구독 직후 실행할 스레드가 지정된다.
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
