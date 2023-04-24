package io.guatimosim1.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
