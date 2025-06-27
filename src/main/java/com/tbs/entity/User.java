package com.tbs.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User extends BaseClass {
    private String name;
    private String email;
    private String phoneNumber;
}
