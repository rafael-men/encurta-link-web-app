package com.rafael_men.encurta_link_api.dto;


import com.rafael_men.encurta_link_api.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapperConverter {

    public UserDto toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getUsername()
        );
    }

    public User toEntity(UserDto userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());

        return user;
    }
}