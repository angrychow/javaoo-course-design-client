package entity;

import java.util.UUID;

public class User {
    public String name;
    public int ID;

    public User(String name) {
        this.name = name;
//        this.ID = UUID.randomUUID().toString();
    }

    public User(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return String.format("%-4d %s", ID, name);
    }
}
