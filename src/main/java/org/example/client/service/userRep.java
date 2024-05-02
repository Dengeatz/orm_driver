package org.example.client.service;


import org.example.client.repos.UserRepository;
import org.example.server.tools.CRUDRepository;

import java.sql.SQLException;


public class userRep implements UserService{
    private UserRepository userRepository;

    @Override
    public void addEntityToDB(String tableName, Object o) throws SQLException {
        userRepository.addEntityToDB(tableName,o);
    }
}
