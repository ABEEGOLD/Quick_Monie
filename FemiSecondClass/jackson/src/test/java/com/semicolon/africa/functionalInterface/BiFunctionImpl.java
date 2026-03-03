package com.semicolon.africa.functionalInterface;

import java.util.function.BiFunction;

public class BiFunctionImpl {
    public static void main(String[] args) {
        /*
         * - BiFunction:
         * a functional interface that takes
         * two things and returns something else
         */
        BiFunction <String, Long, String> biFunction =
                (name, number) -> "name:" + name + " number:" + number;
        System.out.println(biFunction.apply("Abby", 30L));
    }
}
