package org.example;

import org.example.Entity.User;
import org.example.Interface.UserOperation;

import java.util.HashMap;

public class UserService implements UserOperation {
    HashMap<String, User> users = new HashMap<>();

    public void addUser(User user){
        this.users.put(user.getId(), user);
    }

    @Override
    public User register(String name, String email) {
        User user = new User(name, email);
        this.addUser(user);
        return user;
    }

    @Override
    public User getUser(String userId) {
        return this.users.getOrDefault(userId, null);
    }
}
