package com.carrot.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerNewSingleExample implements ApplicationRunner {

    // Schedulers.newSingle() 은 스레드를 선언할 때 마다 새로운 스레드 생성
    // 2번째 파라미터인 daemon 스레드 동작 여부는, true로 설정한다면 main 스레드가 종료될 때 같이 종료
    // 지연 시간이 짧은 작업을 처리하는 데 효과적
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.newSingle("test thread", true))
                .doOnNext(data -> log.info("# doOnNext: fromArray {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .publishOn(Schedulers.newSingle("test thread", true))
//                .publishOn(Schedulers.newSingle("test thread", false)) << 메인스레드와 같이 종료되지 않는다.
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
