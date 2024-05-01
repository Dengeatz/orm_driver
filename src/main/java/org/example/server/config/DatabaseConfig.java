package org.example.server.config;

import org.example.client.config;
import org.example.server.config.annotations.BirdBootApplication;
import org.example.server.config.annotations.Config;
import org.example.server.config.annotations.Table;
import org.example.server.database.Database;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.SQLOutput;

import static org.example.server.database.Database.database;


/**
 * Поиск аннотации
 */
public class DatabaseConfig {
    /**
     * Задаем главный путь для поиска всех аннотации
     *
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */

    private static final DatabaseConfig databaseConfig;

    static {
        try {
            databaseConfig = new DatabaseConfig();
        } catch (ClassNotFoundException | NoSuchFieldException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConfig dbConfig() {
        return databaseConfig;
    }
    private DatabaseConfig() throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, SQLException {
        File file = new File("src/main/java/org/example/client");
        File[] fileList = file.listFiles();
        for(File f : fileList){
            readFlags(isDir(f)); // Рекурсия для получения всех классов, вне зависимости оттого в какой они папке
        }
    }

    public File returnChild(File file) throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        File filePath = new File(file.getPath());
        File[] fileList = filePath.listFiles();
        for (File f : fileList) {
            return f;
        }
        return null;
    }

    public File isDir(File file) throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(file.isDirectory() == true) {
            return isDir(returnChild(file));
        } else {
            return file;
        }
    }

    public void readFlags(File f) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, SQLException {
        Class cl = Class.forName(f.getPath().toString().replaceAll("\\\\", ".").replace(".java", "").substring(9));
        for(Annotation anno : cl.getAnnotations()) {
            if(anno.annotationType() == Config.class) { // Аннотация @Config
                Object o = cl.getDeclaredConstructor().newInstance();
                cl.getDeclaredField("DB_NAME").setAccessible(true);
                Field DB_NAME = cl.getDeclaredField("DB_NAME");
                DB_NAME.setAccessible(true);
                String name = DB_NAME.get(o).toString();
                Field DB_PASSWORD = cl.getDeclaredField("DB_PASSWORD");
                DB_PASSWORD.setAccessible(true);
                String password = DB_PASSWORD.get(o).toString();
                Field DB_USER = cl.getDeclaredField("DB_USER");
                DB_USER.setAccessible(true);
                String user = DB_USER.get(o).toString();
                database().SET_DB_NAME(name);
                database().SET_DB_USER(user);
                database().SET_DB_PASSWORD(password);
                System.out.println("Данные установлены");
            }
            if(anno.annotationType() == BirdBootApplication.class) {
                org.example.server.boot.Main.main();
            }
        }
    }
}
