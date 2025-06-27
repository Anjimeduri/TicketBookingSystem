package com.tbs.services;

import lombok.extern.slf4j.Slf4j;
import com.tbs.entity.Venue;
import com.tbs.repository.VenueRepository;
import com.tbs.services.serviceInterfaces.IVenueService;

@Slf4j
public class VenueService extends BaseService<Venue> implements IVenueService {
    public VenueService(VenueRepository venueRepository) {
        super(venueRepository);
    }
}
