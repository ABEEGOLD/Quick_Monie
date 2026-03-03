package com.semicolon.africa.functionalInterface;

import java.util.function.Function;

public class FunctionImpl {
    public static void main(String[] args) {
        Function<String, Integer> function = (String name) -> name.length();
        System.out.println(function.apply("Abby"));
    }
}
