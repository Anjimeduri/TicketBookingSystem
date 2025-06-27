package org.tbs.repository;

import org.tbs.entity.User;

public class UserRepository extends BaseRepository<User> {
    @Override
    protected Long getId(User entity) {
        return entity.getId();
    }

    @Override
    protected void setId(User entity, Long id) {
        entity.setId(id);
    }
}
