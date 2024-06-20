// File: src/main/java/models/PersonDAO.java
package models;

import java.util.List;

public interface PersonDAO {
    List<Person> getAllPersons();
    Person getPersonById(int id);
    void addPerson(Person person);
    void updatePerson(Person person);
    void deletePerson(int id);
}
