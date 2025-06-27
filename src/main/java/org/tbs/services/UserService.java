package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.User;
import org.tbs.repository.UserRepository;
import org.tbs.services.serviceInterfaces.IUserService;

@Slf4j
public class UserService extends BaseService<User> implements IUserService {
    public UserService(UserRepository repository) {
        super(repository);
    }
}
