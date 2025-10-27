package com.ramoncollectibles.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.ramoncollectibles.model.User;

public class UserService {

    private Map<String, User> users = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    public UserService() {
        String initialId = String.valueOf(counter.incrementAndGet());
        users.put(initialId, new User(initialId, "Rafael Developer", "rafael@example.com"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(String id) {
        return users.get(id);
    }

    public User addUser(User user) {
        String newId = String.valueOf(counter.incrementAndGet());
        user.setId(newId); 
        users.put(newId, user);
        return user;
    }

    public User updateUser(String id, User user) {
        if (users.containsKey(id)) {
            user.setId(id); 
            users.put(id, user);
            return user;
        }
        return null; 
    }

    public boolean userExists(String id) {
        return users.containsKey(id);
    }

    public boolean deleteUser(String id) {
        return users.remove(id) != null;
    }
}