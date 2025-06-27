package com.tbs.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.tbs.enums.ShowStatus;

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
}
