package org.tbs.services.serviceInterfaces;

import org.tbs.entity.User;

public interface IUserService {
    User register(String name, String email, String phoneNumber);

    User findById(Long id);
}
