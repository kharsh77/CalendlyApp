package org.example.Entity;

public class User {
    static int counter = 0;
    String id;
    String name;
    String email;

    public User(String name, String email){
        id= Integer.toString(++counter);
        this.name=name;
        this.email=email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
