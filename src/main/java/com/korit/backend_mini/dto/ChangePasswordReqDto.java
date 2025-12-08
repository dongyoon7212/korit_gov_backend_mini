package com.korit.backend_mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordReqDto {
    private Integer userId;
    private String password;
    private String newPassword;
}
