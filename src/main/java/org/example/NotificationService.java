package org.example;

import org.example.Entity.OneEvent;
import org.example.Entity.User;
import org.example.Interface.NotificationOperations;

import java.util.ArrayList;

public class NotificationService implements NotificationOperations {
    @Override
    public void sendInvite(OneEvent event, User user) {

    }

    @Override
    public void sendInvite(OneEvent event, ArrayList<User> users) {

    }
}
