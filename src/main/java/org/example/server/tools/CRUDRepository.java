package org.example.server.tools;

import java.sql.SQLException;

public interface CRUDRepository {
    void addEntityToDB(String tableName, Object o) throws SQLException;

}
