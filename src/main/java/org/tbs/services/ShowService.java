package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.Show;
import org.tbs.enums.ShowStatus;
import org.tbs.services.serviceInterfaces.IShowService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ShowService implements IShowService {

    private Map<Long, Show> showsMap;

    public ShowService() {
        this.showsMap = new ConcurrentHashMap<>();
    }

    @Override
    public Show register(Long venueId, Long eventId, ShowStatus showStatus, LocalDateTime startTime, LocalDateTime endTime, LocalDate showDate) {
        Show show = new Show(venueId, eventId, showDate, startTime, endTime, showStatus);
        showsMap.put(show.getId(), show);
        log.info("Show registered with id -> {}", show.getId());
        return show;
    }

    @Override
    public Show findById(Long id) {
        if (!showsMap.containsKey(id)) {
            throw new IllegalArgumentException("Show not found with the provided ID");
        }
        return showsMap.get(id);
    }
}
