package io.guatimosim1.model.factory;

import io.guatimosim1.model.dto.UserResponseDTO;
import io.guatimosim1.model.entity.User;

public class UserFactory {
    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO().builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
