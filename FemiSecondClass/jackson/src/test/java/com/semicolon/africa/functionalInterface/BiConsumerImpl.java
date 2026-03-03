package com.semicolon.africa.functionalInterface;

import java.util.function.BiConsumer;

public class BiConsumerImpl {
    public static void main(String[] args) {
        BiConsumer<String, Long> biConsumer = (word1, word2) -> {
            String text = "My name is %s and I am %d";
            System.out.println(String.format(text, word1, word2));
        };
        biConsumer.accept("Abby", 45L);
    }
}
