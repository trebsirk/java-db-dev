import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAOs.DataAccessException;
import DAOs.PersonDAO;
import DAOs.UserSessionDAOImpl;
import models.CrudLogEvent;
import models.CrudResult;
import models.Person;
import models.UserSession;
import redis.clients.jedis.JedisPoolConfig;
import services.CrudService;
import services.DBConnection;
import services.UserSessionService;
import utils.CLI;
import utils.DAOAction;

public class Main {
    public static void main(String[] args)  {
        // crudDemo();
        kvDemo();
    }

    static void kvDemo() {
        UserSessionDAOImpl di = new UserSessionDAOImpl(new JedisPoolConfig(), "localhost", 6379, new ObjectMapper());
        System.out.println(di);
        UserSessionService s = new UserSessionService(di);
        System.out.println(s);

        try {
            UserSession us = s.createSession(1, 10000);
            System.out.println(us);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    static void crudDemo() {
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
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
