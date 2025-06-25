package org.tbs.services.serviceInterfaces;

import org.tbs.entity.Show;
import org.tbs.enums.ShowStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IShowService {
    Show register(Long venueId, Long eventId, ShowStatus showStatus, LocalDateTime startTime, LocalDateTime endTime, LocalDate showDate);

    Show findById(Long id);
}
