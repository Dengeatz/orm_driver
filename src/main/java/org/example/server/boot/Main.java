package org.example.server.boot;

import org.example.server.config.DatabaseConfig;
import org.example.server.config.SQLConfig;

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
