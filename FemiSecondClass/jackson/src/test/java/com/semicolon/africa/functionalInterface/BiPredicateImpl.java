package com.semicolon.africa.functionalInterface;

import java.util.function.BiPredicate;

public class BiPredicateImpl {
    public static void main(String[] args) {
        BiPredicate<Integer, Integer>  isGreater = (num1, num2) -> num1 > num2;
        System.out.println(isGreater.test(15, 10));
        System.out.println(isGreater.test(5, 10));
    }
}
