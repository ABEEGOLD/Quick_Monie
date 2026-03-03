package com.semicolon.africa.functionalInterface;

import java.util.function.UnaryOperator;

public class UnaryOperatorImpl {
    public static void main(String[] args) {
        UnaryOperator<String> operator = (name) -> name.toUpperCase();
        System.out.println(operator.apply("Abby"));
    }
}
