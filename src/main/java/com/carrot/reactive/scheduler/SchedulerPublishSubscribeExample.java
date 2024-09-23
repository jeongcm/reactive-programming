package com.carrot.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerPublishSubscribeExample implements ApplicationRunner {

    // subscribeOn 이 publishOn 과 같이 선언이 될 경우, publishOn 이 선언 되기 이전의 operator chain은 subscribeOn 에서 선언한
    // 스레드에서 실행된다.
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext: fromArray {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
