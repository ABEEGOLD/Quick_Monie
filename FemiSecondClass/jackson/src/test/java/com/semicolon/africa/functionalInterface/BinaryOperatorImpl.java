package com.semicolon.africa.functionalInterface;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorImpl {
    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.naturalOrder();
         BinaryOperator<Integer> maxBy = BinaryOperator.maxBy(comparator);
            System.out.println(maxBy.apply(10,30));

    }
}
