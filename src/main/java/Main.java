import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import DAOs.PersonDAO;
import models.CrudLogEvent;
import models.CrudResult;
import models.Person;
import services.CrudService;
import services.DBConnection;
import utils.CLI;
import utils.DAOAction;

public class Main {
    public static void main(String[] args) throws SQLException {
        Optional<Connection> optConn = new DBConnection().getOptConnection();
        Connection conn = optConn.orElseThrow();

        PersonDAO pdao = new PersonDAO(conn);
        CrudService<Person> pservice = new CrudService<Person>(pdao);

        List<DAOAction> ch = Arrays.asList(DAOAction.values());
        List<String> choices = ch.stream().map((DAOAction v) -> v.name()).toList();
        
        DAOAction choice = new CLI().readChoice(choices);

        switch (choice) {
            case GET:
                Person person = pservice.getById(1);
                System.out.println(person);
                break;
            case GETALL:
                List<Person> persons = pservice.getAll();
                persons.forEach(p -> System.out.println(p));
                break;
            case ADD:
                CrudResult resCre = pservice.create(new Person(null, "tony", 42), new CrudLogEvent(System.currentTimeMillis(), "create", false, null, -1));
                System.out.println(resCre);
                break;
            case DELETE:
                CrudResult resDel = pservice.delete(3, new CrudLogEvent(System.currentTimeMillis(), "delete", false, null, -1));
                System.out.println(resDel);
                break;
            default:
                System.out.println("error: bad input");
                break;
        }
        conn.close();
    }
}
