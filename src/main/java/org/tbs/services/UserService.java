package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.User;
import org.tbs.services.serviceInterfaces.IUserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class UserService implements IUserService {
    private Map<Long, User> usersMap;

    public UserService() {
        this.usersMap = new ConcurrentHashMap<>();
    }

    @Override
    public User register(String name, String email, String phoneNumber) {
        User user = new User(name, email, phoneNumber);
        usersMap.put(user.getId(), user);
        log.info("User registered with id -> {}", user.getId());
        return user;
    }

    @Override
    public User findById(Long id) {
        if (!usersMap.containsKey(id)) {
            throw new IllegalArgumentException("User not found with the provided ID");
        }
        return usersMap.get(id);
    }
}
