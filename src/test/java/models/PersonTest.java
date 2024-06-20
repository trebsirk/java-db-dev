// File: src/test/java/models/PersonTest.java
package models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PersonTest {

    @Test
    public void testPerson() {
        Person person = new Person(null, "John Doe", 30);
        assertEquals("John Doe", person.name());
        assertEquals(Integer.valueOf(30), person.age());
    }
}
