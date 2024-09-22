package com.carrot.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerParallelExample implements ApplicationRunner {

    // reactor 의 scheduler 는 비동기 프로그래밍 스레드를 관리하기 위한 역할
    // parallel 은 병렬성으로 n개의 스레드가 생성
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31})
                .parallel(4)
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(100L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
