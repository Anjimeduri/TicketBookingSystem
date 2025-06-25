package org.tbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.tbs.utils.IdGenerator;

@Getter
@Setter
public class User extends BaseClass {
    private String name;
    private String email;
    private String phoneNumber;

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.setId(IdGenerator.generateSequence(this.getClass()));
    }
}
