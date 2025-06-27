package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.Venue;
import org.tbs.repository.VenueRepository;
import org.tbs.services.serviceInterfaces.IVenueService;

@Slf4j
public class VenueService extends BaseService<Venue> implements IVenueService {
    public VenueService(VenueRepository venueRepository) {
        super(venueRepository);
    }
}
