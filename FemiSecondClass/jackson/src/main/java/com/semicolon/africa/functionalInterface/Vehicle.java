package com.semicolon.africa.functionalInterface;
@FunctionalInterface
public interface Vehicle {
    void move();
    default void reverse() {
        System.out.println("reverse");
    }
}
