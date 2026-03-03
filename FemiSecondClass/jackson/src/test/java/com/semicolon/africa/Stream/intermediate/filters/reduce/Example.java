package com.semicolon.africa.Stream.intermediate.filters.reduce;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Example {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,10);
        Stream<Integer> integerStream = numbers.stream();
        //The most popular one is using this stream method directly
//        Stream<Integer> stream = numbers.stream();

        Stream<Integer> stream = Stream.of(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

//        stream.forEach(System.out::println);

        IntStream intstream = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

//        Stream.generate(() -> "Hello")
        Stream.generate(() -> new Random().nextInt(10))
                .limit(10)
                .forEach(System.out::println);
        //generate create an infinite stream.
    }
}
