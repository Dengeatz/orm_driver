package org.example.client.service;



import org.example.client.repos.UserRepository;
import org.example.server.tools.CRUDRepository;

import java.sql.SQLException;


public class userRep implements UserService {

    private final CRUDRepository userRepository;
    public userRep(CRUDRepository crud) {
        this.userRepository = crud;
    }

    @Override
    public void addEntityToDB(String tableName, Object o) throws SQLException {
        userRepository.addEntityToDB(tableName, o);
    }

    @Override
    public void selectUser(int id, String tableName) throws SQLException {
        userRepository.getEntity(id, tableName);
    }
}
