package com.semicolon.africa.functionalInterface;

import java.util.function.Predicate;

public class PredicateFunctionImpl {
    public static void main(String[] args) {
        /*
         * Predicate:
         * functional interface that takes an argument, and returns a boolean
         */
        Predicate<Integer> isPositive = (num) -> num > 0;
        System.out.println(isPositive.test(10));
        System.out.println(isPositive.test(-5));

        Predicate<String> myPredicate = (str) -> str.equals("Hello");
        System.out.println(myPredicate.test("Hell"));
    }
}
