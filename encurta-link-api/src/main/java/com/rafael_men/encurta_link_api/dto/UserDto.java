package com.rafael_men.encurta_link_api.dto;

import java.util.UUID;

public class UserDto {
    private UUID id;
    private String username;

    public UserDto() {}

    public UserDto(UUID id, String username) {
        this.id = id;
        this.username = username;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}