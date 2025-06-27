package org.tbs.repository;

import org.tbs.entity.Booking;

public class BookingRepository extends BaseRepository<Booking> {
    @Override
    protected Long getId(Booking entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Booking entity, Long id) {
        entity.setId(id);
    }
}
