package com.carrot.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerPublishExample implements ApplicationRunner {

    // reactor 의 scheduler 는 비동기 프로그래밍 스레드를 관리하기 위한 역할
    // publishOn 은 DownStream 으로 signal 전송할 때 실행되는 스레드를 제어하는 역할
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
