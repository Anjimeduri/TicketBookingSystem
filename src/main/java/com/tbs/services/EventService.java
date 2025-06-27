package com.tbs.services;

import lombok.extern.slf4j.Slf4j;
import com.tbs.entity.Event;
import com.tbs.repository.EventRepository;
import com.tbs.services.serviceInterfaces.IEventService;

@Slf4j
public class EventService extends BaseService<Event> implements IEventService {
    public EventService(EventRepository eventRepository) {
        super(eventRepository);
    }
}
