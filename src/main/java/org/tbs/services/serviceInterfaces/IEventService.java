package org.tbs.services.serviceInterfaces;

import org.tbs.entity.Event;

public interface IEventService {
    Event register(String name, String description, Integer duration, String uom);

    Event findById(Long id);
}
