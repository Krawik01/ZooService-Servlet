package service;

import dbController.DbConnect;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

@Dependent
public class AnimalService implements IAnimalService {

    private final DbConnect dbConnect = new DbConnect();


    @Override
    public String getAnimalsList() {
        String result = "";

        result = dbConnect.execSql("SELECT * FROM animals ");
        System.out.println("this is result: " + result);
        return result;
    }

}
