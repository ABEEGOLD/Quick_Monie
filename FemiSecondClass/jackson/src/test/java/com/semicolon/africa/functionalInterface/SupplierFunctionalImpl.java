package com.semicolon.africa.functionalInterface;

import java.util.Random;
import java.util.function.Supplier;

public class SupplierFunctionalImpl {
    public static void main(String[] args) {
        Supplier<Integer> integerSupplier = () -> new Random().nextInt(0,5);
        System.out.println(integerSupplier.get());

    }
}
