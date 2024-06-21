// File: src/main/java/models/PersonDAO.java
package DAOs;

import java.util.List;

public interface DAO<T> {
    
    T getById(int id);
    List<T> getAll();
    void add(T t);
    void update(T t);
    void delete(int id);
}
