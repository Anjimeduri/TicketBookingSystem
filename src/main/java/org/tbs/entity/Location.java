package org.tbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.tbs.utils.IdGenerator;

@Getter
@Setter
public class Location extends BaseClass {
    private Integer latitude;
    private Integer longitude;
    private String area;
    private Integer pincode;

    public Location(Integer latitude, Integer longitude, String area, Integer pincode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.area = area;
        this.pincode = pincode;
        this.setId(IdGenerator.generateSequence(this.getClass()));
    }
}
