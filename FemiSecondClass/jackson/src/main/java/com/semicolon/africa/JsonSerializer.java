package com.semicolon.africa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerializer {
    private final static ObjectMapper mapper = new ObjectMapper();
    public static String serializer(Person person){
        try{
            return mapper.writeValueAsString(person);
        }catch (JsonProcessingException e){
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
    public static Person deserialize(String json){
        try{
            Person person = mapper.readValue(json,Person.class);
            return person;
        }catch (IOException e){
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
