package com.tbs.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Event extends BaseClass {
    private String name;
    private String description;
    private Integer duration;
    private String uom;
}
