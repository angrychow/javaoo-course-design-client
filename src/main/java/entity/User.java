package entity;

import java.util.UUID;

public class User {
    public String name;
    public String ID;

    public User(String name) {
        this.name = name;
//        this.ID = UUID.randomUUID().toString();
    }

    public User(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }
}
