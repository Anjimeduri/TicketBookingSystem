package org.tbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.tbs.utils.IdGenerator;

@Getter
@Setter
public class Event extends BaseClass {
    private String name;
    private String description;
    private Integer duration;
    private String uom;

    public Event(String name, String description, Integer duration, String uom) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.uom = uom;
        this.setId(IdGenerator.generateSequence(this.getClass()));
    }
}
