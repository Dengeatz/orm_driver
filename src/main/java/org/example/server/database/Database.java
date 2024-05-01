package org.example.server.database;

import org.example.server.config.DatabaseConfig;
import org.example.server.tools.CRUDRepository;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

public class Database implements CRUDRepository {

    private static String DB_NAME;
    private static String DB_USER;
    private static String DB_PASSWORD;




    private static String DB_URL;
    private static final String DB_DRIVER = "org.postgresql.Driver";

    private static Connection con = null;
    private static final Database database;



    static {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Database database() {
        return database;
    }


    private Object o;

    public void setConnect() throws SQLException {
        DB_URL = String.format("jdbc:postgresql://localhost:5432/%s?user=%s&password=%s", DB_NAME, DB_USER, DB_PASSWORD);
        con = DriverManager.getConnection(DB_URL);
        con.setAutoCommit(true);
        System.out.println("База данных подключена");
    }


    private Database() throws SQLException {
        try {
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void SET_DB_NAME(String DB_NAME) {
        Database.DB_NAME = DB_NAME;
    }

    public void SET_DB_USER(String DB_USER) {
        Database.DB_USER = DB_USER;
    }

    public void SET_DB_PASSWORD(String DB_PASSWORD) {
        Database.DB_PASSWORD = DB_PASSWORD;
    }

    private Map readEntity(Class cl, Object oo) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, SQLException {
        Map<String, String> entities = new LinkedHashMap<>();

        for(Field f : cl.getDeclaredFields()) {
            f.setAccessible(true);
            String name = f.get(oo).toString();
            entities.put(f.getName(),name);
        }
        return entities;
    }

    public void addEntityToDB(String tableName, Object o) throws SQLException {
        try {
            Map<String, String> entities = readEntity(o.getClass(), o);
            int id = 0;
            for(int i = 0; i < entities.keySet().size(); i++) {
                String query = "";
                if(entities.keySet().toArray()[i] == "id") {
                    id = Integer.parseInt(entities.values().toArray()[i].toString());
                    query = String.format("INSERT INTO %s (%s) VALUES (%d)", tableName, entities.keySet().toArray()[i], id);
                } else {
                    query = String.format("UPDATE %s SET %s = '%s' WHERE id=%d", tableName, entities.keySet().toArray()[i], entities.values().toArray()[i], id);
                }
                System.out.println(query);
                PreparedStatement s = con.prepareStatement(query);
                s.execute();
                s.close();
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
//        String query = String.format("INSERT INTO %s ()", tableName);
//        PreparedStatement s = con.prepareStatement(query);
//        s.execute();
//        s.close();
//        System.out.println("d");
    }

    public void selectAllEntity(String tableName) throws SQLException {
        String query = String.format("SELECT * FROM %s", tableName);
        PreparedStatement s = con.prepareStatement(query);
        ResultSet rs = s.executeQuery();
        Map<String, String> Entities = new LinkedHashMap<>();
        while(rs.next()) {
            Entities.put(rs.getString("id"), tableName);
        }
        s.close();
        System.out.println(Entities);
    }


    public void printSchemas(String name) throws SQLException {
        PreparedStatement s = con.prepareStatement("SELECT schema_name FROM information_schema.schemata WHERE schema_name=?;");
        s.setString(1, name);
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(rs.getRow()));
        }
        s.close();

    }


    public void addSchema(String nameOfSchema) throws SQLException {
        String query = String.format("CREATE SCHEMA IF NOT EXISTS %s", nameOfSchema);
        PreparedStatement s = con.prepareStatement(query);
        s.execute();
        s.close();

        System.out.println("Schema добавлена!");
    }

    public void dropTable(String tableName) throws SQLException {
        String query = String.format("DROP TABLE IF EXISTS %s", tableName);
        PreparedStatement s = con.prepareStatement(query);
        s.execute();
        s.close();

        System.out.println("Таблица успешно ликвидирована");
    }

    public void addTable(String tableName, Class c) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Map<String, String> fieldValue = new LinkedHashMap<String, String>();
        for (Field field : c.getDeclaredFields()) {
            field.setAccessible(true);
            fieldValue.put(field.getName(), field.getType().getTypeName());
        }
        Object[] keys = fieldValue.keySet().toArray();
        Object[] values = fieldValue.values().toArray();

        String query = String.format("CREATE TABLE IF NOT EXISTS %s ()", tableName.toLowerCase());
        PreparedStatement s = con.prepareStatement(query);
        s.execute();
        s.close();
        for (int i = 0; i < keys.length; i++) {
            if (values[i] == "int" & keys[i] == "id") {
                query = String.format("ALTER TABLE %s ADD %s INTEGER PRIMARY KEY", tableName, keys[i]);
                s = con.prepareStatement(query);
                s.execute();
                s.close();
            }
            if (values[i] == "int" & keys[i] != "id") {
                query = String.format("ALTER TABLE %s ADD %s INTEGER", tableName, keys[i]);
                s = con.prepareStatement(query);
                s.execute();
                s.close();
            }
            if (values[i] == "java.lang.String") {
                query = String.format("ALTER TABLE %s ADD %s VARCHAR(200)", tableName, keys[i]);
                s = con.prepareStatement(query);
                s.execute();
                s.close();
            }
        }

        System.out.println("Таблица добавлена!");
    }

    public boolean isTableExists(String tableName) throws SQLException {
        String query = String.format("SELECT true AS COUNT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '%s' LIMIT 1", tableName.toLowerCase());
        PreparedStatement s = con.prepareStatement(query);
        boolean rs = s.execute();
        if (rs) {
            return true;
        } else {
            return false;
        }
    }


}



//    public void addUserToTable(String tableName, User o) {
//        String query = String.format("INSERT INTO %s ()");
//        PreparedStatement s = con.prepareStatement(query);
//        s.execute();
//        s.close();
//        System.out.println("Пользователь успешно добавлен в таблицу!");
//    }
