package com.carrot.reactive.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@Slf4j
public class MapExample implements ApplicationRunner {

    public static void main(String[] args) throws InterruptedException, IOException {
        // map 예제
        Flux.just("1-Circle", "3-Circle", "5-Circle")
                .map(circle -> circle.replace("Circle", "Rectangle"))
                .subscribe(data -> log.info("# on Next: {}", data));

        // flatMap 예제
        Flux.just("Good", "Bad")
                .flatMap(feeling -> Flux
                        .just("Morning", "afternoon", "Evening")
                        .map(time -> feeling + " " + time)
                ).subscribe(log::info);

        // zip 예제
        Flux.zip(Flux.just(1, 2, 3).delayElements(Duration.ofMillis(300L)),
                Flux.just(4, 5, 6).delayElements(Duration.ofMillis(500L))
        ).subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(2500L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
