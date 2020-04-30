package com.learning.springbootapp;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class User {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
