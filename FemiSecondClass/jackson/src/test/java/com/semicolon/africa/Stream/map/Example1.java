package com.semicolon.africa.Stream.map;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Example1 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> set = numbers.stream()
                .map((number)->String.valueOf(number * 2))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
