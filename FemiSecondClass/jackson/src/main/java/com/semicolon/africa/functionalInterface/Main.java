package com.semicolon.africa.functionalInterface;

public class Main {
    public static void main(String[] args) {
       Vehicle vehicle = () ->{
           System.out.println("Car Moving....");
       };
       vehicle.move();




    }
}
