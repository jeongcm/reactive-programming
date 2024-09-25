package com.carrot.reactive.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
public class PostBookExample implements ApplicationRunner {
    public static final String HEADER_AUTH_TOKEN = "authorization";

    // context 는 인증 벙보와 같은 직교성(독립성)을 가지는 정보를 전송하는데 적합하다.
    public static void main(String[] args) {
        Mono<String> mono = postBook(Mono.just(new Book(
                        "aaaa-bbbb-cccc-dddd",
                        "carrot example",
                        "carrot jeong"
                ))
        )
                .contextWrite(Context.of(HEADER_AUTH_TOKEN, "APILFKDLK143!"));

        mono.subscribe(data -> log.info("# onNext: {}", data));

    }

    private static Mono<String> postBook(Mono<Book> book) {
        return Mono
                .zip(book,
                        Mono
                                .deferContextual(ctx -> Mono.just(ctx.get(HEADER_AUTH_TOKEN)))
                )
                .flatMap(tuple -> {
                    String response = "POST the book(" + tuple.getT1().getName() + ", " + tuple.getT1().getAuthor() +
                            ") with token: " + tuple.getT2();

                    return Mono.just(response);
                });
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}


@AllArgsConstructor
@Data
class Book {
    private String isbn;
    private String name;
    private String author;
}