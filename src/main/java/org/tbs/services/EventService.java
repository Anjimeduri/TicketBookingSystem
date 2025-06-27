package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.Event;
import org.tbs.repository.EventRepository;
import org.tbs.services.serviceInterfaces.IEventService;

@Slf4j
public class EventService extends BaseService<Event> implements IEventService {
    public EventService(EventRepository eventRepository) {
        super(eventRepository);
    }
}
