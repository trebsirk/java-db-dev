// File: src/main/java/models/PersonDAO.java
package DAOs;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    
    T getById(int id);
    List<T> getAll();
    void add(T t);
    void update(T t);
    void delete(int id);
    Optional<T> fromJSON(String jsonString); // parse json to record
    String toJSON(T t);
}
