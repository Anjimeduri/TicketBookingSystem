package org.tbs.repository;

import org.tbs.entity.Show;

public class ShowRepository extends BaseRepository<Show> {
    @Override
    protected Long getId(Show entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Show entity, Long id) {
        entity.setId(id);
    }
}
