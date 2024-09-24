package com.carrot.reactive.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextExample implements ApplicationRunner {

    // context 는 구독이 발생할 때마다 해당 구독과 연결된 하나의 Context 가 생긴다.
    // context 내 key/value 로 데이터 저장
    public static void main(String[] args) throws InterruptedException {
        // deferContextual 은 원본 데이터 소스 레벨에서 context의 값을 읽어 온다.
        // defer 함수 처럼 context 에 저장된 데이터와 원본 데이터 소스의 처리를 지연
        // 이때 defer에서 읽어오는 파라미터는 context 가 아닌 contextView
        // context 쓸때는 context, 읽을 때는 contextView
        Mono.deferContextual(ctx ->
                        Mono.just("Hello" + " " + ctx.get("firstName"))
                                .doOnNext(data -> log.info(" # just doOnNext : {}", data))
                ) // subscribe on scheduler 에서 실행
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                // operator 체인에서 context 의 값을 읽어오는 방식은 transformDeferredContextual 이다.
                .transformDeferredContextual(
                        (Mono, ctx) -> Mono.map(data -> data + " " + ctx.get("middleName") + " " + ctx.get("lastName") +
                                ctx.get("dog"))
                ) // publish on scheduler  에서 실행
                .transformDeferredContextual(
                        (Mono, ctx) -> Mono.map(data -> data + ", dog: " + ctx.get("dog") + " name: " +
                                ctx.getOrDefault("name", "yakkoo"))
                ) // publish on scheduler  에서 실행

                // context.putAll 은 contextView 타입을 context에 merge
                // 먼저 선언되야함.
                .contextWrite(context -> context.putAll
                        (Context.of("dog", "pome", "dogName", "야꾸").readOnly())
                )

                // contextWrite operator에 context put 한 불변 객체를 전달 -> Thread safe 보장
                .contextWrite(context -> context.put("middleName", "chang"))
                .contextWrite(context -> context.put("firstName", "jeong"))
                // context.of 는 여러개의 context 전달
                .contextWrite(context -> Context.of("lastName", "myung", "nickName", "carrot"))

                .subscribe(data -> log.info(" # onNext: {}", data));

        Thread.sleep(100L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
