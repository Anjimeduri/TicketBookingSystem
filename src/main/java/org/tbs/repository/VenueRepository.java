package org.tbs.repository;

import org.tbs.entity.Venue;

public class VenueRepository extends BaseRepository<Venue> {
    @Override
    protected Long getId(Venue entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Venue entity, Long id) {
        entity.setId(id);
    }
}
