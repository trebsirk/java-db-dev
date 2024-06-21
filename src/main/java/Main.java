import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import models.DatabaseUtils;
import models.Person;
import models.PersonDAO;
import services.DBConnection;

public class Main {
    public static void main(String[] args) {
        
        Optional<Connection> optConn = new DBConnection().getOptConnection();
        Connection conn = optConn.orElseThrow();
        // Connection conn = new DBConnection().getConnection();
        
        // PersonDAO personDAO = new DatabaseUtils(optConn.orElseThrow());
        PersonDAO personDAO = new DatabaseUtils(conn);

        // Use the DAO methods
        List<Person> persons = personDAO.getAllPersons();
        persons.forEach(person -> 
            System.out.println("Name: " + person.name() + ", Age: " + person.age()));
    }
}
