// File: src/test/java/models/PersonTest.java
package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import DAOs.PersonDAOImpl;

public class PersonTest {

    @Test
    public void testPerson() {
        Person person = new Person(null, "John Doe", 30);
        assertEquals("John Doe", person.name());
        assertEquals(Integer.valueOf(30), person.age());
    }

    @Test
    public void testPersonFromJSON() {
        String jsonString = """
        {
            "id": 1,
            "name": "Cleetus",
            "age": 30
        }
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals((int)p.id(), 1);
        assertEquals("Cleetus", p.name());
        assertEquals((int)p.age(), 30);
    }

    @Test
    public void testPersonToJSON() {
        String jsonStringExpected = """
        {"id":1,"name":"Cleetus","age":30}
        """;
        Person p = new Person(1, "Cleetus", 30);
        String jsonString = new PersonDAOImpl(null).toJSON(p);
        assertTrue(jsonStringExpected.trim().equals(jsonString.trim()));
    }
}
