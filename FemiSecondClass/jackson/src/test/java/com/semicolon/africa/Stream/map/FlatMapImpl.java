package com.semicolon.africa.Stream.map;

import java.util.List;


public class FlatMapImpl {
    public static void main(String[] args) {
        List<String> words = List.of("QWERTY","ASDFG","ZXCVB");
        List<Integer> result = words.stream()//["QWERTY","ASDFG","ZXCVB"]
                .flatMap((word)-> word.chars().boxed())//[81,87,69...] [65,83,...] [90,88,...]
                .toList();
        System.out.println(result);
    }
}
