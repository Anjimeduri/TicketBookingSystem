package com.tbs.services;

import lombok.extern.slf4j.Slf4j;
import com.tbs.entity.User;
import com.tbs.repository.UserRepository;
import com.tbs.services.serviceInterfaces.IUserService;

@Slf4j
public class UserService extends BaseService<User> implements IUserService {
    public UserService(UserRepository repository) {
        super(repository);
    }
}
