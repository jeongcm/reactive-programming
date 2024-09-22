package com.carrot.reactive.funcInterface;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PredicateExample implements ApplicationRunner {
    public static void main(String[] args) {
        List<String> testData = new ArrayList<>();
        testData.add("jeong");
        testData.add("chang");
        testData.add("myung");
        testData.add("car");

        List<String> filtered = filter(testData, td -> td.length() > 3);

        String name = sum(filtered, data -> data.toUpperCase());
        System.out.println(name);
    }

    private static List<String> filter(List<String> testString, Predicate<String> predicate) {
        List<String> result = new ArrayList<>();
        for (String s : testString) {
            if (predicate.test(s)) {
                result.add(s);
            }

        }
        return result;
    }

    private static String sum(List<String> testString, Function<String, String> fuc) {
        StringBuilder result = new StringBuilder();

        result.append("제 이름은 ");

        testString.forEach(s -> {
            result.append(fuc.apply(s));
            if (testString.indexOf(s) != testString.size() - 1) {
                result.append(" ");
            }

        });

        result.append("입니다. 잘 부탁드립니다.");

        return result.toString();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
