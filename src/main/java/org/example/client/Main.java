package org.example.client;


import org.example.client.models.User;
import org.example.client.repos.UserRepository;
import org.example.client.service.userRep;
import org.example.server.config.annotations.BirdBootApplication;

import java.sql.SQLException;

import static org.example.server.database.Database.database;




@BirdBootApplication
public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, SQLException {

            org.example.server.boot.Main.main();
            database().addEntityToDB("users", new User(1, "Ilya"));

//        userRep ur = new userRep();
//        ur.addEntityToDB("users", new User(3, "Ilya"));
    }
}