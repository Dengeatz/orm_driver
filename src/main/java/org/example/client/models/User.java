package org.example.client.models;

import org.example.server.config.annotations.Entity;
import org.example.server.config.annotations.Table;

@Table()
@Entity
public class User{
    private int id;

    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
