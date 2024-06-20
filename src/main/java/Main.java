import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import models.DatabaseUtils;
import models.Person;
import models.PersonDAO;

public class Main {
    public static void main(String[] args) {
        String dbname = System.getenv("PG_DBNAME");
        String host = System.getenv("PG_HOST");
        String port = System.getenv("PG_PORT");
        String user = System.getenv("PG_USER");
        String password = System.getenv("PG_DBNAME");

        String url = "jdbc:postgresql://" + host + ":"+port+"/"+dbname;
        System.out.println(url);
        
        try {
            Class.forName("org.postgresql.Driver"); // Explicitly load the driver
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                PersonDAO personDAO = new DatabaseUtils(connection);

                // Use the DAO methods
                List<Person> persons = personDAO.getAllPersons();
                persons.forEach(person -> 
                    System.out.println("Name: " + person.name() + ", Age: " + person.age()));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
