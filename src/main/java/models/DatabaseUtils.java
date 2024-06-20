// File: src/main/java/models/DatabaseUtils.java
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils implements PersonDAO {

    private final Connection connection;

    public DatabaseUtils(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT id, name, age FROM persons";

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
    public Person getPersonById(int id) {
        String query = "SELECT name, age FROM persons WHERE id = ?";
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
    public void addPerson(Person person) {
        String query = "INSERT INTO persons (name, age) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, person.name());
            if (person.age() != null) {
                preparedStatement.setInt(2, person.age());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePerson(Person person) {
        String query = "UPDATE persons SET name = ?, age = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, person.name());
            if (person.age() != null) {
                preparedStatement.setInt(2, person.age());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setInt(3, person.id()); // Assuming Person has an 'id' field
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(int id) {
        String query = "DELETE FROM persons WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
