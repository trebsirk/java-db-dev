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
        {"id": 1,"name": "Cleetus","age": 30}
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals((int)p.id(), 1);
        assertEquals("Cleetus", p.name());
        assertEquals((int)p.age(), 30);
    }

    @Test
    public void testPersonFromJSONWithMissingId() {
        String jsonString = """
        {"name": "Cleetus", "age": 123}
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals(p.id(), null);
        assertEquals(p.name(), "Cleetus");
        assertEquals((int)p.age(), 123);
    }

    @Test
    public void testPersonFromJSONWithNullId() {
        String jsonString = """
        {"id": null,"name": "Cleetus", "age": 123}
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals(p.id(), null);
        assertEquals(p.name(), "Cleetus");
        assertEquals((int)p.age(), 123);
    }

    @Test
    public void testPersonFromJSONWithMissingAge() {
        String jsonString = """
        {"id": 1,"name": "Cleetus"}
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals((int)p.id(), 1);
        assertEquals(p.name(), "Cleetus");
        assertEquals(p.age(), null);
    }

    @Test
    public void testPersonFromJSONWithNullAge() {
        String jsonString = """
        {"id": 1,"name": "Cleetus", "age": null}
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals((int)p.id(), 1);
        assertEquals(p.name(), "Cleetus");
        assertEquals(p.age(), null);
    }

    @Test
    public void testPersonFromJSONWithMissingName() {
        String jsonString = """
        {"id": 111,"age":222}
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals((int)p.id(), 111);
        assertEquals(p.name(), null);
        assertEquals((int)p.age(), 222);
    }

    @Test
    public void testPersonFromJSONWithNullName() {
        String jsonString = """
        {"id": 111,"name":null, "age":222}
        """;
        Optional<Person> pOpt = new PersonDAOImpl(null).fromJSON(jsonString);
        assertFalse(pOpt.isEmpty());
        Person p = pOpt.get();//.orElseGet(() -> new Person(null, null, null));
        assertEquals((int)p.id(), 111);
        assertEquals(p.name(), null);
        assertEquals((int)p.age(), 222);
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
