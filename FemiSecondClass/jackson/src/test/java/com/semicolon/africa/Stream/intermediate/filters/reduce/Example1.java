package com.semicolon.africa.Stream.intermediate.filters.reduce;

import java.util.Arrays;
import java.util.List;

public class Example1 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I","J");
        list.stream()//[A, B, C, D, E, F, G, H, I,J]
                .filter((letter)-> letter.matches("[AEIOU]"))//["A","E"]
                .forEach((letter)->System.out.println(letter));
                    //forEach takes a consumer here
    }
}
