package org.example.server.boot;

import org.example.client.repos.UserRepository;
import org.example.client.service.userRep;
import org.example.server.config.DatabaseConfig;
import org.example.server.config.SQLConfig;
import org.example.server.database.Database;

import javax.xml.crypto.Data;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import static org.example.server.config.DatabaseConfig.dbConfig;
import static org.example.server.database.Database.database;


public class Main {



    public static DatabaseConfig databaseConfig = null;
    public static void main() throws SQLException {

    }




    static {
        try {
            database();
            dbConfig();
            database().setConnect();
            database().selectAllEntity("users");
            SQLConfig.SQLConfig();
            System.out.println("Сканирование аннотации завершено, все SQL запросы успешно сделаны");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
