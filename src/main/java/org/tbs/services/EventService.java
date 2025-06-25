package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.Event;
import org.tbs.services.serviceInterfaces.IEventService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EventService implements IEventService {
    private Map<Long, Event> eventsMap;

    public EventService() {
        this.eventsMap = new ConcurrentHashMap<>();
    }

    @Override
    public Event register(String name, String description, Integer duration, String uom) {
        Event event = new Event(name, description, duration, uom);
        eventsMap.put(event.getId(), event);
        log.info("Event registered with id -> {}", event.getId());
        return event;
    }

    @Override
    public Event findById(Long id) {
        if (!eventsMap.containsKey(id)) {
            throw new IllegalArgumentException("Event not found with the provided ID");
        }
        return eventsMap.get(id);
    }
}
