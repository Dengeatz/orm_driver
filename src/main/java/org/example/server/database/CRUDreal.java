package org.example.server.database;

import org.example.server.tools.CRUDRepository;

import javax.sql.rowset.CachedRowSet;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class CRUDreal implements CRUDRepository {
    private static final CRUDreal cruDreal = new CRUDreal();
    private CRUDreal(Object o) {

        System.out.println("Объект создан");
    }


    public static CRUDreal getCruDreal() {
        return cruDreal;
    }

    @Override
    public void addEntityToDB(String tableName, Object o) throws SQLException {
        System.out.println(tableName);
    }
}
