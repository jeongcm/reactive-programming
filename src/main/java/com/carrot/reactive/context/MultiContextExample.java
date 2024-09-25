package com.carrot.reactive.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class MultiContextExample implements ApplicationRunner {

    public static void main(String[] args) throws InterruptedException {
        final String key1 = "carrot";
        final String key2 = "jeong";

        Mono<String> mono = Mono.deferContextual(ctx ->
                        Mono.just("Carrot: " + ctx.get(key1)))
                .publishOn(Schedulers.parallel());

        mono.contextWrite(ctx -> ctx.put(key1, "first ctx"))
                .subscribe(data -> log.info("# subscribe1 onNext: {}", data));

        // subscribe 2 에서만 추가 ctx key2 값이 보여짐
        // ctx 의 경우 operator 체인상의 아래에서 위로 전파되는 특징이 있다.
        // 따라서 모든 operator 에서 context에 저장된 데이터를 읽을 수 있도록, contextWrite()를 operator 체인 맨 마지막에 둬야함.
        mono.contextWrite(ctx -> ctx.put(key1, "second ctx"))
                .transformDeferredContextual((m, ctx) ->
                        m.map(data -> data + ctx.getOrDefault(key2, " jeong")))
                .subscribe(data -> log.info("# subscribe2 onNext: {}", data));


        // inner Sequence 외부에서는 inner sequence 내부 context에 저장된 데이터를 읽을수 없다.
        Mono.just("changMyung")
//                .transformDeferredContextual(((m, ctx) -> ctx.get("role")))
                .flatMap(name ->
                        Mono.deferContextual(ctx ->
                                Mono.just(ctx.get(key1) + ", " + name)
                                        .transformDeferredContextual((mm, innerCtx) -> mm.map(data -> data + ", " + innerCtx.get("role")))
                        ))
                .contextWrite(context -> context.put("role", "CEO"))
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "vegetable"))
                .subscribe(data -> log.info("# onNext: {}", data));
        Thread.sleep(100L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
