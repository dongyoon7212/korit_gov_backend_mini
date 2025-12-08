package com.korit.backend_mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeUsernameReqDto {
    private Integer userId;
    private String username;
}
