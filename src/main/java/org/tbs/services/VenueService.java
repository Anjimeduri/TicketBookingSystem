package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.Location;
import org.tbs.entity.Venue;
import org.tbs.services.serviceInterfaces.IVenueService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class VenueService implements IVenueService {
    private Map<Long, Venue> venueMap;

    public VenueService() {
        this.venueMap = new ConcurrentHashMap<>();
    }

    @Override
    public Venue register(String name, String area, Integer latitude, Integer longitude, Integer pincode) {
        Location location = new Location(latitude, longitude, area, pincode);
        Venue venue = new Venue(name, location, true, 10, 10);
        venueMap.put(venue.getId(), venue);
        log.info("Venue create with id -> {}", venue.getId());
        return venue;
    }

    @Override
    public Venue findById(Long id) {
        if (!venueMap.containsKey(id)) {
            throw new IllegalArgumentException("Venue not found with the provided ID");
        }
        return venueMap.get(id);
    }
}
