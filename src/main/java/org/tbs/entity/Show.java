package org.tbs.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tbs.enums.ShowStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Show extends BaseClass {
    private Long venueId;
    private Long eventId;
    private LocalDate showDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ShowStatus showStatus;

//    public Show(Long venueId, Long eventId, LocalDate showDate, LocalDateTime startTime, LocalDateTime endTime, ShowStatus showStatus) {
//        this.venueId = venueId;
//        this.eventId = eventId;
//        this.showDate = showDate;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.showStatus = ShowStatus.YET_TO_START;
//    }
}
