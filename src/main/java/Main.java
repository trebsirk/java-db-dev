import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import DAOs.PersonDAOImpl;
import models.Person;
import services.DBConnection;

public class Main {
    public static void main(String[] args) {
        
        Optional<Connection> optConn = new DBConnection().getOptConnection();
        Connection conn = optConn.orElseThrow();
        // Connection conn = new DBConnection().getConnection();
        
        // PersonDAO personDAO = new DatabaseUtils(optConn.orElseThrow());
        PersonDAOImpl pdaoimpl = new PersonDAOImpl(conn);

        // Use the DAO methods
        List<Person> persons = pdaoimpl.getAll();
        persons.forEach(person -> 
            System.out.println("Name: " + person.name() + ", Age: " + person.age()));
    }
}
