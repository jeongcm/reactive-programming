package com.carrot.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerSingleExample implements ApplicationRunner {

    // Schedulers.single() 은 하나의 스레드로  scheduler 가 제거 되기 전가지 사용
    // 지연 시간이 짧은 작업을 처리하는 데 효과적
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.single())
                .doOnNext(data -> log.info("# doOnNext: fromArray {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .publishOn(Schedulers.single())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
