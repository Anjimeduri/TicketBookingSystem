package org.tbs.services.serviceInterfaces;

import org.tbs.entity.Venue;

public interface IVenueService {
    Venue register(String name, String area, Integer latitude, Integer longitude, Integer pincode);

    Venue findById(Long id);
}
