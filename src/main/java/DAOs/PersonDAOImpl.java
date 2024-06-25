package DAOs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.CrudLogEvent;
import models.Person;

public class PersonDAOImpl implements PersonDAO {

    private final Connection connection;
    private final String table = "persons";
    private static final Logger logger = Logger.getLogger(PersonDAOImpl.class.toString());


    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person getById(int id) {

        long startTime = System.currentTimeMillis();
        CrudLogEvent msg = null;
        
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
            long endTime = System.currentTimeMillis();
            msg = new CrudLogEvent(startTime, "getById", true, endTime-startTime);

        } catch (SQLException e) {
            e.printStackTrace();
            long endTime = System.currentTimeMillis();
            msg = new CrudLogEvent(startTime, "getById", false, endTime-startTime);
        }
        
        logger.info(msg.toString());
        return person;
    }
    
    @Override
    public List<Person> getAll() {
        
        long startTime = System.currentTimeMillis();
        CrudLogEvent msg = null;

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
            long endTime = System.currentTimeMillis();
            msg = new CrudLogEvent(startTime, "getById", true, endTime-startTime);

        } catch (SQLException e) {
            e.printStackTrace();
            long endTime = System.currentTimeMillis();
            msg = new CrudLogEvent(startTime, "getById", false, endTime-startTime);
        }

        logger.info(msg.toString());
        return persons;
    }

    @Override
    public void add(Person p) {
        String query = "INSERT INTO " + table + " (name, age) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, p.name());
            if (p.age() != null) {
                preparedStatement.setInt(2, p.age());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Person p) {
        String query = "UPDATE " + table + " SET name = ?, age = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, p.name());
            if (p.age() != null) {
                preparedStatement.setInt(2, p.age());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setInt(3, p.id()); // Assuming Person has an 'id' field
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM " + table + " WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
