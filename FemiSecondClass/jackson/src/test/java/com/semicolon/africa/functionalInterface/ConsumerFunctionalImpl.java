package com.semicolon.africa.functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerFunctionalImpl {
    public static void main(String[] args) {
        Consumer<String> consumer = (String word) -> {
            System.out.println("you entered: " + word);
        };
        List<String> words = Arrays.asList("hello", "hello");
        words.forEach((String word) -> System.out.println("you entered:" + word));
    }
}
