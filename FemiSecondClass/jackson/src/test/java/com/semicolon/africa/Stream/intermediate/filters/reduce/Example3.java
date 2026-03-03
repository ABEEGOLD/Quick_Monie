package com.semicolon.africa.Stream.intermediate.filters.reduce;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Example3 {
    public static void main(String[] args) {
        List<Integer> list = List.of(10, 20, 30, 40, 50, 60, 70, 80, 90,100);
//        list.stream()
                Integer sum = list.stream()
                .reduce(0, (identity,element)-> identity + element);
                System.out.println(sum);

                Set<Integer> nums = Set.of(2,3,4);
                Integer product = nums.stream()
                        .reduce(1,(identity,element)-> identity * element);
                System.out.println(product);
    }
}
