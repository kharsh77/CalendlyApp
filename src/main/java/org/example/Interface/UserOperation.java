package org.example.Interface;

import org.example.Entity.User;

public interface UserOperation {
    User register(String name, String email);
    User getUser(String userId);
}
