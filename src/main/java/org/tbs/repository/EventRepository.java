package org.tbs.repository;

import org.tbs.entity.Event;

public class EventRepository extends BaseRepository<Event> {
    @Override
    protected Long getId(Event entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Event entity, Long id) {
        entity.setId(id);
    }
}
