package io.guatimosim1.exception.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Details {
    private String error;
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
}
