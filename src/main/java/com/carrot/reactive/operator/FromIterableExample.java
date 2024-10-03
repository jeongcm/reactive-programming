package com.carrot.reactive.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;

import java.util.*;

@Slf4j
public class FromIterableExample implements ApplicationRunner {

    public static void main(String[] args) {
        // fromIterable 은 iterable에 포함된 데이터를 emit 하는 flux를 생성한다. iterable 구현체를 fromIterable()에 보낼수 있다.
        List<Map<String, Object>> infos = Arrays.asList(
                createInfo("정창명", 15),
                createInfo("정야꿍", 8),
                createInfo("이다봉", 5)
        );

        Flux.fromIterable(infos)
                .subscribe(info -> log.info("이름: {}, 나이: {}", info.get("이름"), info.get("나이")));
    }


    private static Map<String, Object> createInfo(String name, int age) {
        Map<String, Object> info = new HashMap<>();
        info.put("이름", name);
        info.put("나이", age);
        return info;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
