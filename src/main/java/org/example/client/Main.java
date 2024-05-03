package org.example.client;


import org.example.client.models.User;

import org.example.client.service.userRep;
import org.example.server.config.annotations.BirdBootApplication;

import java.sql.SQLException;

import static org.example.server.database.Database.database;


@BirdBootApplication
public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, SQLException {

            org.example.server.boot.Main.main();

        userRep ur = new userRep(database());
        ur.addEntityToDB("users", new User(3, "John"));
        ur.selectUser(3, "users");

    }
}