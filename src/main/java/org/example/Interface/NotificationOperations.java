package org.example.Interface;

import org.example.Entity.OneEvent;
import org.example.Entity.User;

import java.util.ArrayList;

public interface NotificationOperations {
    void sendInvite(OneEvent event, User user);
    void sendInvite(OneEvent event, ArrayList<User> users);
}
