package org.example.client.service;

import java.sql.SQLException;

public interface UserService {
    void addEntityToDB(String tableName, Object o) throws SQLException;
}
