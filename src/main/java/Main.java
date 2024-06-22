import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import DAOs.PersonDAOImpl;
import models.Person;
import services.DBConnection;
import utils.CLI;
import utils.DAOAction;

public class Main {
    public static void main(String[] args) {
        Optional<Connection> optConn = new DBConnection().getOptConnection();
        Connection conn = optConn.orElseThrow();

        PersonDAOImpl pdaoimpl = new PersonDAOImpl(conn);

        List<DAOAction> ch = Arrays.asList(DAOAction.values());
        //ch.stream().map(a -> a);
        List<String> choices = ch.stream().map((DAOAction v) -> v.name()).toList();
        
        DAOAction choice = new CLI().readChoice(choices);

        switch (choice) {
            case GET:
                System.out.println("GET not implemented");
                break;
            case GETALL:
                System.out.println("GETALL selected");
                List<Person> persons = pdaoimpl.getAll();
                persons.forEach(person -> System.out.println(person));
                break;
            case ADD:
                System.out.println("ADD not implemented");
                break;
            case DELETE:
                System.out.println("DELETE not implemented");
                break;
            default:
                System.out.println("error: bad input");
                break;
        }

    }
}
