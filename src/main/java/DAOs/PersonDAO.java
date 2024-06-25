package DAOs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.CrudResult;
import models.Person;

public class PersonDAO implements DAO<Person> {

    private final Connection connection;
    private final String table = "persons";

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person getById(int id) {
        String query = "SELECT name, age FROM " + table + " WHERE id = ?";
        Person person = null;
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer age = resultSet.getObject("age") != null ? resultSet.getInt("age") : null;
                person = new Person(id, name, age);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return person;
    }
    
    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT id, name, age FROM " + table;

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Integer id = resultSet.getObject("id") != null ? resultSet.getInt("id") : null;
                String name = resultSet.getString("name");
                Integer age = resultSet.getObject("age") != null ? resultSet.getInt("age") : null;
                persons.add(new Person(id, name, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public CrudResult add(Person p) {
        String query = "INSERT INTO " + table + " (name, age) VALUES (?, ?)";
        int res = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, p.name());
            if (p.age() != null) {
                preparedStatement.setInt(2, p.age());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            res = preparedStatement.executeUpdate();
            return (res > 0) ? new CrudResult(true) : new CrudResult(false);

        }
        // SQLTimeoutException is a subclass of SQLException, 
        // and must go first; otherwise SQLException will be a 'catch-all' 
        // ... pun intended ... and SQLTimeoutException will be unreachable
        catch (SQLTimeoutException e) { 
            e.printStackTrace();
            return new CrudResult(false, Optional.ofNullable(e.toString()));
        } catch (SQLException e) {
            e.printStackTrace();
            return new CrudResult(false, Optional.ofNullable(e.toString()));
        } 
        
    }

    @Override
    public CrudResult update(Person p) {
        String query = "UPDATE " + table + " SET name = ?, age = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, p.name());
            if (p.age() != null) {
                preparedStatement.setInt(2, p.age());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setInt(3, p.id()); // Assuming Person has an 'id' field
            int res = preparedStatement.executeUpdate();
            return (res > 0) ? new CrudResult(true) : new CrudResult(false);

        } catch (SQLException e) {
            e.printStackTrace();
            new CrudResult(false, Optional.ofNullable(e.toString()));
        }
        return null;
    }

    @Override
    public CrudResult delete(int id) {
        String query = "DELETE FROM " + table + " WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new CrudResult(true);
    }

    @Override
    public Optional<Person> fromJSON(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = null;
        try {
            person = objectMapper.readValue(jsonString, Person.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(person);
    }

    @Override
    public String toJSON(Person p) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(p);
            return jsonString;
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
