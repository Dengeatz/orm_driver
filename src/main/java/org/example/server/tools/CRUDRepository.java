package org.example.server.tools;

import java.sql.SQLException;

public interface CRUDRepository {
    void addEntityToDB(String tableName, Object o) throws SQLException;

    void getEntity(int id, String tableName) throws SQLException;


}
