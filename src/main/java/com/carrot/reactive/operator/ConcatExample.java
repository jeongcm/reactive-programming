package com.carrot.reactive.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@Slf4j
public class ConcatExample implements ApplicationRunner {

    public static void main(String[] args) throws InterruptedException, IOException {
        // concat 예제
        Flux.concat(Flux.just("1-Circle", "3-Circle", "5-Circle"), Flux.just("7-Circle", "9-Circle"))
                .map(circle -> circle.replace("Circle", "Rectangle"))
                //.subscribe(log::info) method reference 를 이용해 출력도 가능
                .subscribe(data -> log.info("# on Next: {}", data));

        // merge 예제 merge operator 의 경우 interleave 방식으로 병합 (교차로 배차 가능 emit 된 시간 순서대로)
        Flux.merge(
                Flux.just(1, 2, 3, 4).delayElements(Duration.ofMillis(300L)),
                Flux.just(5, 6, 7, 8).delayElements(Duration.ofMillis(500L))
        ).subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(2000L);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
