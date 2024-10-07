package com.carrot.reactive.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public class UsingExample implements ApplicationRunner {

    public static void main(String[] args) throws InterruptedException, IOException {
        Path path = Paths.get("C:\\Users\\jcm\\workspace\\reactive" +
                "\\src\\main\\java\\com\\carrot\\reactive\\operator\\test.txt");

        // using 은 첫번째 파라미터가 읽어 올 resource, 두번째 파리미터가 emit하는 flux,
        //  세번째가 종료 signal 이 발생했을 때, 후처리를 할수 있게 해줌
        Flux.using(() -> Files.lines(path), Flux::fromStream, Stream::close)
                .subscribe(log::info);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
