package com.semicolon.africa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {
    @Test
    void testCanSerializeData(){
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAge(30);
        String json = JsonSerializer.serializer(person);
        String expected = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":30}";
        assertEquals(expected,json);
    }
    @Test
    void testCanDeserializeData(){
        String json = "{\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"age\":15}";
        Person person = JsonSerializer.deserialize(json);
        assertNotNull(person);
        assertEquals("Jane",person.getFirstName());
        assertEquals("Smith",person.getLastName());
        assertEquals(15,person.getAge());
    }

}