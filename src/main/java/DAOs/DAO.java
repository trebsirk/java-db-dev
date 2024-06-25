// File: src/main/java/models/PersonDAO.java
package DAOs;

import java.util.List;
import java.util.Optional;

import models.CrudResult;

public interface DAO<T> {
    
    T getById(int id);
    List<T> getAll();
    CrudResult add(T t);
    CrudResult update(T t);
    CrudResult delete(int id);
    Optional<T> fromJSON(String jsonString); // parse json to record
    String toJSON(T t);
}
