package org.example.server.config;

import org.example.server.config.annotations.Entity;
import org.example.server.config.annotations.Table;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import static org.example.server.database.Database.database;

public class SQLConfig {

    private static final SQLConfig sqlConfig;

    static {
        try {
            sqlConfig = new SQLConfig();
        } catch (NoSuchFieldException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SQLConfig SQLConfig() {
        return sqlConfig;
    }
    String tableName;

    private SQLConfig() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, SQLException {
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
            if(anno.annotationType() == Table.class) { // Аннотация @Table
                Table annotation = (Table) cl.getAnnotation(anno.annotationType());
                tableName = annotation.value();
                if (tableName.length() == 0) {
                    tableName = cl.getSimpleName() + "s";

                }
                try {
                    if (database().isTableExists(tableName)) {
                        database().dropTable(tableName);
                    }
                    database().addTable(tableName, cl);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (anno.annotationType() == Entity.class) {
                database().selectAllEntity(tableName);
            }
        }

    }




    public void addUserToDatabase() {
        System.out.println("dsa");
    }
}
