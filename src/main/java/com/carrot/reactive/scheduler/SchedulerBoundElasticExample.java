package com.carrot.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerBoundElasticExample implements ApplicationRunner {

    // Schedulers.boundElastic() 은 excutor service 기반 스레드 풀을 생성한 후, 정해진 수만큼의 스레드를 사용하여 작업을 처리하고
    // 종료된 스레드는 반납하여 재사용하는 방식
    // default 값은 cpu 코어 수 X 10 만큼의 스레드를 생성 최대 100,000개의 작업이 큐에서 대기 가능
    // 데이터 베이스나 http 요청 같은 blocking IO 작업을 효과적으로 처리 가능. -> 실행 시간이 긴 blocking IO 작업이 포함된 경우,
    // 다른 non-blocking 처리에 영향을 주지 않도록 전용 스레드를 할당해 blocking IO 작업을 처리
    // scheduler.newXXXX() 방식은 커스텀 스레드 풀을 생성 가능하다.
    // scheduler.fromExecutorService()는 권장되지 않는다. (이미 사용하고있는 executorService 를 통해 scheduler 생성)
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext: fromArray {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .publishOn(Schedulers.newBoundedElastic(5,50000,"custom BoundElastic" ))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
